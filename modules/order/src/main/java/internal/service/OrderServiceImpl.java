package internal.service;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import internal.model.Order;
import internal.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.mail.MailParseException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import shared.constants.Constants;
import shared.dtos.UserDto;
import shared.enums.*;
import shared.services.EmailService;
import shared.services.OrderService;
import shared.dtos.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shared.services.PaymentService;
import shared.services.UserService;
import shared.types.Location;
import shared.types.LunarDate;
import shared.utils.UtilFunction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public OrderDto createOrder(Long farmerId, CropType cropType, String address, Location location, Double farmlandArea, LocalDate desiredDate, OrderSlot timeSlot) throws Exception {
        int sessionNum = 1;
        Long numOrders = orderRepository.countByDesiredDateAndTimeSlot(desiredDate, timeSlot);

        if (numOrders >= 2) {
            throw new IllegalStateException("The maximum order for this time slot has been reached");
        } else {
           sessionNum += numOrders;
        }

        Order order;

        if (farmerId == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AuthenticationCredentialsNotFoundException("No credentials found for current user");
            }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Long currentUserId = Long.parseLong(userDetails.getUsername());
            order = new Order(null, currentUserId, cropType, address, location, farmlandArea, desiredDate, Constants.UNIT_COST * farmlandArea, timeSlot, OrderStatus.PENDING, PaymentStatus.UNPAID, new ArrayList<>(), sessionNum, null, null);
        } else {
            order = new Order(null, farmerId, cropType, address, location, farmlandArea, desiredDate, Constants.UNIT_COST * farmlandArea, timeSlot, OrderStatus.PENDING, PaymentStatus.UNPAID, new ArrayList<>(), sessionNum, null, null);
        }
        Order savedOrder = orderRepository.save(order);
        UserDto user = userService.getUserById(order.getFarmerId());

        try {
            emailService.sendEmail(user.getEmailAddress(), "Order Creation",
                    "Hello, " + user.getFullName() + "!\n" +
                            "This email is to confirm that you have booked a spraying order at Hoversprite\n" +
                            "Below is the details of your order: \n" +
                            "Order ID: " + savedOrder.getId() + "\n" +
                            "Farmer ID: " + savedOrder.getFarmerId() + "\n" +
                            "Crop Type: " + savedOrder.getCropType() + "\n" +
                            "Desired Date (Gregorian): " + savedOrder.getDesiredDate() + "\n" +
                            "Desired Date (Lunar): " + new LunarDate(savedOrder.getDesiredDate()).toString() + "\n" +
                            "Total Cost: " + savedOrder.getTotalCost() + "\n" +
                            "Time slot: " + savedOrder.getTimeSlot().toString() + "\n" +
                            "Status: " + savedOrder.getStatus() + "\n" +
                            "Session: " + savedOrder.getSession() + "\n" +
                            "Created At: " + savedOrder.getCreatedAt() + "\n"
            );
        } catch (MailParseException e) {
            System.out.print(e);
        }
        return savedOrder;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));
    }

    @Override
    public List<OrderDto> getOrdersByFarmerId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("No credentials found for current user");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = Long.parseLong(userDetails.getUsername());
        List<OrderDto> orderDtos = orderRepository.findAllByFarmerId(currentUserId).stream().map(entity -> (OrderDto) entity).toList();
        System.out.println(orderDtos);
        return orderDtos;
    }

    @Override
    public OrderDto updateOrderStatus(Long id, OrderStatus status) throws Exception {
        UserDetails userDetails = UtilFunction.getUserDetails();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));

        if(status == OrderStatus.CONFIRMED) {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_RECEPTIONIST.name()))) {
                throw new AccessDeniedException("Only receptionists are allowed to confirm an order");
            }
            if (order.getStatus() != OrderStatus.PENDING) {
                throw new IllegalArgumentException("The status of this order is " + order.getStatus().name() + ". You can only confirm PENDING orders.");
            }
            UserDto user = userService.getUserById(order.getFarmerId());

            emailService.sendEmail(user.getEmailAddress(), "Order Confirmation", "This is an email to inform you that order #" + order.getId() + " has been confirmed and we are looking for suitable sprayers to assign.");
        }

        if(status == OrderStatus.IN_PROGRESS) {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_SPRAYER.name()))) {
                throw new AccessDeniedException("Only sprayers are allowed to start the spraying process of an order.");
            }
            if (order.getAssignedSprayerIds().contains(Long.parseLong(userDetails.getUsername()))) {
                throw new AccessDeniedException("You are not assigned to this order. You cannot mark it as in progress.");
            }
            if (order.getStatus() != OrderStatus.ASSIGNED) {
                throw new IllegalArgumentException("The status of this order is " + order.getStatus().name() + ". You can only start an assigned orders.");
            }
        }

        if(status == OrderStatus.FINISHED) {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_FARMER.name()))) {
                throw new AccessDeniedException("Only farmers are allowed to confirmed that the spraying session is finished.");
            }
            if (Long.parseLong(userDetails.getUsername()) != order.getFarmerId()) {
                throw new AccessDeniedException("You are not the booker associated with this order. You are not allowed to mark it as finished.");
            }
            if (order.getStatus() != OrderStatus.ASSIGNED) {
                throw new IllegalArgumentException("The status of this order is " + order.getStatus().name() + ". You can only start an assigned orders.");
            }
        }

        if(status == OrderStatus.COMPLETED) {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_RECEPTIONIST.name()))) {
                throw new AccessDeniedException("Only sprayers are allowed to complete an order an order.");
            }
            if (order.getAssignedSprayerIds().contains(Long.parseLong(userDetails.getUsername()))) {
                throw new AccessDeniedException("You are not assigned to this order. You cannot mark it as completed.");
            }

            if (order.getStatus() != OrderStatus.FINISHED) {
                throw new IllegalArgumentException("The status of this order is " + order.getStatus().name() + ". You can only start an assigned orders.");
            }
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public OrderDto assignSprayer(Long id, List<Long> sprayerIds) throws Exception {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));
        order.setAssignedSprayerIds(sprayerIds);
        order.setStatus(OrderStatus.ASSIGNED);
        Order savedOrder = orderRepository.save(order);
        UserDto user = userService.getUserById(order.getFarmerId());


        List<UserDto> sprayers = sprayerIds.stream().map(sprayerId -> {
            try {
                return userService.getUserById(sprayerId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();

        emailService.sendEmail(user.getEmailAddress(), "Order Confirmation", "This is an email to inform you that order #" + order.getId() + " has been assigned to sprayer(s) with the following names: " + sprayers.stream().map(sprayer -> sprayer.getFullName() + " ") );

        sprayers.stream().map(sprayer -> {
            emailService.sendEmail(sprayer.getEmailAddress(), "Order Assign",
                    "This is an email to inform you that you have been assigned to order #" + order.getId() + "\n" +
                            "Farmer Information: \n" +
                            "Full name: " + user.getFullName() + "\n" +
                            "Location: " + order.getLocation() + "\n" +
                            "Phone Number: " + user.getPhoneNumber()
            );
            return null;
        });
        return savedOrder;
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
        UserDetails userDetails = UtilFunction.getUserDetails();
        OrderDto orderDto = getOrderById(id);
        if (!userDetails.getUsername().equals(String.valueOf(orderDto.getFarmerId()))) {
            throw new BadCredentialsException("You are not the farmer associated with this order");
        }

        Payment payment = paymentService.createPayment(orderDto.getTotalCost(), "USD", "paypal", "sale", "Payment for order #" + orderDto.getId(), cancelUrl, successUrl);

        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return link.getHref();
            }
        }

        throw new PayPalRESTException("Paypal approval URL not found");
    }

    public String executePayment(Long id, String paymentId, String payerId) throws Exception {
        UserDetails userDetails = UtilFunction.getUserDetails();
        OrderDto orderDto = getOrderById(id);
        if (!userDetails.getUsername().equals(String.valueOf(orderDto.getFarmerId()))) {
            throw new BadCredentialsException("You are not the farmer associated with this order");
        }

        Payment payment = paymentService.executePayment(paymentId, payerId);

        if (payment.getState().equals("approved")) {
            Order order = (Order) orderDto;
            order.setPaymentStatus(PaymentStatus.PAID);
            orderRepository.save(order);
            return "Payment success";
        } else {
            throw new PayPalRESTException("Payment failed. Please check your PayPal account");
        }
    }


}
