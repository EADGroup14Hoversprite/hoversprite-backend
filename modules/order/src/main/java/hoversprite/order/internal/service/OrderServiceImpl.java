package hoversprite.order.internal.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import hoversprite.common.external.enums.*;
import hoversprite.email.external.service.EmailService;
import hoversprite.common.external.enums.CropType;
import hoversprite.common.external.enums.OrderSlot;
import hoversprite.common.external.enums.OrderStatus;
import hoversprite.notification.external.service.NotificationService;
import hoversprite.order.internal.model.Order;
import hoversprite.order.internal.repository.OrderRepository;
import hoversprite.payment.external.service.PaymentService;
import hoversprite.user.external.dto.UserDto;
import hoversprite.user.external.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import hoversprite.common.external.constant.Constants;
import hoversprite.order.external.service.OrderService;
import hoversprite.order.external.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hoversprite.common.external.type.Location;
import hoversprite.common.external.util.UtilFunctions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private NotificationService notificationService;

    public OrderDto createOrder(CropType cropType, String farmerName, String farmerPhoneNumber, String farmerEmailAddress, String address, Location location, Double farmlandArea, LocalDate desiredDate, OrderSlot timeSlot) throws Exception {

        List<Order> numOrders = orderRepository.getPendingOrdersByDesiredDateAndTimeSlot(desiredDate, timeSlot);
        if (numOrders.size() >= 2) {
            throw new DataIntegrityViolationException("Maximum number of order for this time slot has been reached");
        }
        
        if (farmlandArea == 0) {
            throw new BadRequestException("Farmland area must be larger than zero.");
        }
        
        if (desiredDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("You cannot book an order in the past");
        }

        UserDetails userDetails = UtilFunctions.getUserDetails();
        Long currentUserId = Long.parseLong(userDetails.getUsername());
        Order order = new Order(null, currentUserId, cropType, farmerName, farmerPhoneNumber, farmerEmailAddress, address, location, farmlandArea, desiredDate, Constants.UNIT_COST * farmlandArea, timeSlot, PaymentMethod.CASH,userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_RECEPTIONIST.name())) ? OrderStatus.CONFIRMED : OrderStatus.PENDING, false, new ArrayList<>(), false, null, null);
        Order savedOrder = orderRepository.save(order);
        UserDto user = userService.getUserById(order.getBookerId());

        emailService.sendOrderCreationEmail(savedOrder.getFarmerEmailAddress(), savedOrder.getFarmerName(), savedOrder.getId(), savedOrder.getFarmerName(), savedOrder.getFarmerPhoneNumber(), savedOrder.getAddress(), savedOrder.getCropType(), savedOrder.getDesiredDate(), savedOrder.getFarmlandArea(), savedOrder.getTotalCost(), savedOrder.getTimeSlot(), savedOrder.getStatus(), savedOrder.getCreatedAt());
        if (!user.getEmailAddress().equals(savedOrder.getFarmerEmailAddress())) {
            emailService.sendOrderCreationEmail(user.getEmailAddress(), user.getFullName(), savedOrder.getId(), savedOrder.getFarmerName(), savedOrder.getFarmerPhoneNumber(), savedOrder.getAddress(), savedOrder.getCropType(), savedOrder.getDesiredDate(), savedOrder.getFarmlandArea(), savedOrder.getTotalCost(), savedOrder.getTimeSlot(), savedOrder.getStatus(), savedOrder.getCreatedAt());
        }

        notificationService.sendNotificationToUser(String.valueOf(user.getId()), "Your order #" + savedOrder.getId() + " has been created.");
        notificationService.broadcastNotificationToAllUsersExceptTrigger(String.valueOf(user.getId()), "A new order has been added. Please refetch for updated orders list.");

        return savedOrder;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));
    }

    public Page<Order> getAllOrders(int page, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return orderRepository.findAll(pageable);
    }

    public List<OrderDto> getAllOrdersByDesiredDate(LocalDate desiredDate) {
        return orderRepository.findAllByDesiredDate(desiredDate).stream().map(entity -> (OrderDto) entity).toList();
    }

    public Page<Order> getOrdersByBookerId(int page, int pageSize, String sortBy, String sortDirection) throws Exception {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        Long currentUserId = Long.parseLong(userDetails.getUsername());
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return orderRepository.findAllByBookerId(currentUserId, pageable);
    }

    @Override
    public OrderDto updateOrderStatus(Long id, OrderStatus status) throws Exception {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));

        switch (status) {
            case CANCELLED:
                if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_RECEPTIONIST.name())) &&
                        !userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_FARMER.name()))) {
                    throw new AccessDeniedException("Only farmers/receptionists are allowed to confirm an order");
                }
                if (!String.valueOf(order.getBookerId()).equals(userDetails.getUsername())) {
                    throw new AccessDeniedException("You are not the booker of this order. You cannot cancel it.");
                }
                break;

            case CONFIRMED:
                if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_RECEPTIONIST.name()))) {
                    throw new AccessDeniedException("Only receptionists are allowed to confirm an order");
                }
                if (order.getStatus() != OrderStatus.PENDING) {
                    throw new IllegalArgumentException("The status of this order is " + order.getStatus().name() + ". You can only confirm PENDING orders.");
                }
                UserDto user = userService.getUserById(order.getBookerId());
                emailService.sendOrderConfirmationEmail(order.getFarmerEmailAddress(), order.getFarmerName(), order.getId());
                if (!user.getEmailAddress().equals(order.getFarmerEmailAddress())) {
                    emailService.sendOrderConfirmationEmail(user.getEmailAddress(), user.getFullName(), order.getId());
                }
                break;

            case IN_PROGRESS:
                if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_SPRAYER.name()))) {
                    throw new AccessDeniedException("Only sprayers are allowed to start the spraying process of an order.");
                }
                if (!order.getAssignedSprayerIds().contains(Long.parseLong(userDetails.getUsername()))) {
                    throw new AccessDeniedException("You are not assigned to this order. You cannot mark it as in progress.");
                }
                if (order.getStatus() != OrderStatus.ASSIGNED) {
                    throw new IllegalArgumentException("The status of this order is " + order.getStatus().name() + ". You can only start assigned orders.");
                }
                break;

            default:
                throw new BadRequestException("You cannot update this status at this endpoint.");
        }

        order.setStatus(status);
        notificationService.sendNotificationToUser(userDetails.getUsername(), "Your order #" + order.getId() + " has been set to " + status);
        return orderRepository.save(order);
    }

    @Override
    public OrderDto assignSprayer(Long id, List<Long> sprayerIds) throws Exception {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));

        List<UserDto> sprayers = sprayerIds.stream().map(sprayerId -> {
            try {
                return userService.getUserById(sprayerId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();

        if (sprayers.size() == 1 && sprayers.getFirst().getExpertise() != Expertise.EXPERT) {
            throw new BadRequestException("Only expert sprayer can handle a spray session alone. Try again.");
        } else if (sprayers.size() == 2 && sprayers.getFirst().getExpertise() == Expertise.EXPERT && sprayers.getLast().getExpertise() == Expertise.EXPERT) {
            throw new BadRequestException("One expert sprayer is enough to handle a spray session.");
        }   else if (sprayers.size() == 2 && sprayers.getFirst().getExpertise() == Expertise.APPRENTICE && sprayers.getLast().getExpertise() == Expertise.APPRENTICE) {
            throw new BadRequestException("An apprentice sprayer must have another sprayer adept or expert.");
        }

        order.setAssignedSprayerIds(sprayerIds);
        order.setStatus(OrderStatus.ASSIGNED);
        Order savedOrder = orderRepository.save(order);
        UserDto user = userService.getUserById(order.getBookerId());


        emailService.sendEmail(order.getFarmerEmailAddress(), "Order Confirmation",
                "Hello, " + order.getFarmerName() + "\n" +
                "This is an email to inform you that order #" + order.getId() + " has been assigned to sprayer(s) with the following names: \n" + sprayers.stream().map(sprayer -> sprayer.getFullName() + " \n")
        );
        if (!order.getFarmerEmailAddress().equals(user.getEmailAddress())) {
            emailService.sendEmail(user.getEmailAddress(), "Order Confirmation",
                    "Hello, " + user.getFullName() + "\n" +
                            "This is an email to inform you that order #" + order.getId() + " has been assigned to sprayer(s) with the following names: \n" + sprayers.stream().map(sprayer -> sprayer.getFullName() + " \n")
            );
        }
        notificationService.sendNotificationToUser(String.valueOf(user.getId()), "Your order #" + order.getId() + " has been assigned to sprayers.");

        sprayers.stream().map(sprayer -> {
            emailService.sendOrderAssignmentEmail(sprayer.getEmailAddress(), sprayer.getFullName(), order.getId(), order.getFarmerName(), order.getFarmerPhoneNumber(), order.getAddress(), order.getCropType(), order.getDesiredDate(), order.getFarmlandArea(), order.getTotalCost(), order.getTimeSlot());
            try {
                notificationService.sendNotificationToUser(String.valueOf(sprayer.getId()), "You have been assigned to order #" + order.getId());
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            return null;
        });

        return savedOrder;
    }

    @Override
    public OrderDto updateOrderFeedback(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order id not found"));
        order.setHasFeedback(true);
        return orderRepository.save(order);
    }

    public List<UserDto> getSuggestedSprayers(Long id, LocalDate startDate, LocalDate endDate) throws Exception {
        List<UserDto> sprayers = userService.getUsersByUserRole(UserRole.ROLE_SPRAYER);

        List<OrderDto> ordersWithinWeek = getOrdersWithinDateRange(startDate, endDate);

        // 1. This collects the set of ids of sprayers that are assigned to orders already within the week
        Set<Long> assignedSprayerIds = ordersWithinWeek.stream().flatMap(order -> order.getAssignedSprayerIds().stream()).collect(Collectors.toSet());

        // This filter the assigned sprayers from all sprayers in the system, and return the list of free sprayer in the week -> Prioritize sprayers with no orders in the same week
        List<UserDto> freeSprayers = sprayers.stream().filter(sprayer -> !assignedSprayerIds.contains(sprayer.getId())).toList();

        // If all sprayers have order in the same week then we evaluate using all sprayer.
        if (freeSprayers.isEmpty()) {
            freeSprayers = sprayers;
        }

        // Separate available sprayers to lists based on expertise
        List<UserDto> apprenticeSprayers = freeSprayers.stream().filter(sprayer -> sprayer.getExpertise() == Expertise.APPRENTICE).toList();
        List<UserDto> adeptSprayers = freeSprayers.stream().filter(sprayer -> sprayer.getExpertise() == Expertise.ADEPT).toList();
        List<UserDto> expertSprayers = freeSprayers.stream().filter(sprayer -> sprayer.getExpertise() == Expertise.EXPERT).toList();


        List<UserDto> assignedSprayers = new ArrayList<>();

        // 2. Prioritize apprentice sprayer if available
        if (!apprenticeSprayers.isEmpty()) {

            // 3. If expert sprayer available, choose 1 to accompany
            if (!expertSprayers.isEmpty()) {
                assignedSprayers.add(apprenticeSprayers.getFirst());
                assignedSprayers.add(expertSprayers.getFirst());

                // Else if adept sprayer available, choose 1 to accompany
            } else if (!adeptSprayers.isEmpty()) {
                assignedSprayers.add(apprenticeSprayers.getFirst());
                assignedSprayers.add(adeptSprayers.getFirst());
            }

            // Else if expert sprayer is available
        } else if (!expertSprayers.isEmpty()) {
            assignedSprayers.add(expertSprayers.getFirst());
            // Else if at least 2 adept sprayer is available
        } else if (adeptSprayers.size() >= 2) {
            assignedSprayers.add(adeptSprayers.getFirst());
            assignedSprayers.add(adeptSprayers.getLast());
        }

        if (assignedSprayers.isEmpty()) {
            return null;
        }
        return assignedSprayers;
    }


    public List<OrderDto> getOrdersWithinDateRange(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findAllWithinDateRange(startDate, endDate).stream().map(entity -> (OrderDto) entity).toList();
    }

    public String createPayment(Long id, String successUrl, String cancelUrl) throws Exception {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        OrderDto orderDto = getOrderById(id);
        if (!userDetails.getUsername().equals(String.valueOf(orderDto.getBookerId()))) {
            throw new BadCredentialsException("You are not the farmer associated with this order");
        }

        if (orderDto.getPaymentStatus()) {
            throw new BadRequestException("This order is already paid");
        }

        Payment payment = paymentService.createPayment(orderDto.getTotalCost() * Constants.VND_TO_USD_CONVERSION_RATE, "USD", "paypal", "sale", "Payment for order #" + orderDto.getId(), cancelUrl, successUrl);

        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return link.getHref();
            }
        }

        throw new PayPalRESTException("Paypal approval URL not found");
    }

    public String executePayment(Long id, String paymentId, String payerId) throws Exception {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        OrderDto orderDto = getOrderById(id);
        if (!userDetails.getUsername().equals(String.valueOf(orderDto.getBookerId()))) {
            throw new BadCredentialsException("You are not the farmer associated with this order");
        }

        Payment payment = paymentService.executePayment(paymentId, payerId);

        if (payment.getState().equals("approved")) {
            Order order = (Order) orderDto;
            order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
            order.setPaymentStatus(true);
            orderRepository.save(order);
            return "Payment success";
        } else {
            throw new PayPalRESTException("Payment failed. Please check your card and try again.");
        }
    }

    public String confirmCashPayment(Long id) throws Exception {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        OrderDto orderDto = getOrderById(id);
        List<Long> sprayerIds = orderDto.getAssignedSprayerIds();
        if (!orderDto.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            throw new BadRequestException("You can only confirm cash payment when the order is in progress.");
        }

        if (!sprayerIds.contains(Long.valueOf(userDetails.getUsername()))) {
            throw new AccessDeniedException("You are not assigned to this order. You cannot confirm cash payment for it.");
        }

        Order order = (Order) orderDto;
        order.setPaymentStatus(true);
        orderRepository.save(order);

        return "Cash payment confirmed for order #" + orderDto.getId();
    }

    public List<OrderDto> getOrdersBySprayerId() throws Exception {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        return orderRepository.findAllOrdersByAssignedSprayerIds(Long.valueOf(userDetails.getUsername())).stream().map(entity -> (OrderDto) entity).toList();
    }

    public byte[] generateOrderCompleteQrCode(Long id) throws Exception {
        OrderDto orderDto = getOrderById(id);
        UserDetails userDetails = UtilFunctions.getUserDetails();
        if (!orderDto.getAssignedSprayerIds().contains(Long.valueOf(userDetails.getUsername()))) {
            throw new AccessDeniedException("You are not a sprayer assigned to this order. You cannot generate a QR code to complete it.");
        }
        if(!orderDto.getStatus().equals(OrderStatus.IN_PROGRESS)) {
            throw new BadRequestException("This order is not in progress. You cannot generate a QR code to complete it.");
        }
        if(!orderDto.getPaymentStatus()) {
            throw new BadRequestException("Please confirm that the order is paid for.");
        }
        return generateQRCode("http://localhost:3000/order/" + id + "/complete", 250, 250);
    }

    public Boolean completeOrder(Long id) throws Exception {
        OrderDto orderDto = getOrderById(id);
        UserDetails userDetails = UtilFunctions.getUserDetails();
        if (!orderDto.getBookerId().equals(Long.valueOf(userDetails.getUsername()))) {
            throw new AccessDeniedException("You are not the booker assigned to this order. You cannot generate a QR code to complete it.");
        }
        notificationService.sendNotificationToUser(String.valueOf(orderDto.getBookerId()), "Your order #" + orderDto.getId() + " has been marked as complete.");
        return true;
    }

    public byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }
}
