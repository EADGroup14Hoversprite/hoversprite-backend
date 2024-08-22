package internal.service;

import internal.model.Order;
import internal.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import shared.constants.Constants;
import shared.dtos.order.AssignSprayerRequestDTO;
import shared.dtos.order.UpdateOrderStatusRequestDTO;
import shared.enums.OrderStatus;
import shared.enums.UserRole;
import shared.services.OrderService;
import shared.dtos.order.CreateOrderRequestDTO;
import shared.dtos.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO createOrder(CreateOrderRequestDTO dto) {
        int sessionNum = 1;
        Long numOrders = orderRepository.countByDesiredDateAndTimeSlot(dto.getDesiredDate(), dto.getTimeSlot());

        if (numOrders == 2) {
            throw new IllegalStateException("The maximum order for this time slot has been reached");
        } else {
           sessionNum += numOrders;
        }

        Order order;

        if (dto.getFarmerId() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AuthenticationCredentialsNotFoundException("No credentials found for current user");
            }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Long currentUserId = Long.parseLong(userDetails.getUsername());
            order = new Order(null, currentUserId, dto.getCropType(), dto.getFarmlandArea(), dto.getDesiredDate(),  Constants.UNIT_COST * dto.getFarmlandArea(), dto.getTimeSlot(), OrderStatus.PENDING, new ArrayList<>(), sessionNum, null, null);
        } else {
            order = new Order(null, dto.getFarmerId(), dto.getCropType(), dto.getFarmlandArea(), dto.getDesiredDate(),  Constants.UNIT_COST * dto.getFarmlandArea(), dto.getTimeSlot(), OrderStatus.CONFIRMED, new ArrayList<>(), sessionNum, null, null);
        }
        return orderRepository.save(order);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));
    }

    @Override
    public List<OrderDTO> getOrdersByFarmerId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("No credentials found for current user");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = Long.parseLong(userDetails.getUsername());
        return orderRepository.getOrdersByFarmerId(currentUserId).stream().map(entity -> (OrderDTO) entity).toList();
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, UpdateOrderStatusRequestDTO dto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("No credentials found for current user");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));

        if(dto.getStatus() == OrderStatus.CONFIRMED) {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_RECEPTIONIST.name()))) {
                throw new AccessDeniedException("Only receptionists are allowed to confirm an order");
            }
            if (order.getStatus() != OrderStatus.PENDING) {
                throw new IllegalArgumentException("The status of this order is " + order.getStatus().name() + ". You can only confirm PENDING orders.");
            }
        }

        if(dto.getStatus() == OrderStatus.IN_PROGRESS) {
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

        if(dto.getStatus() == OrderStatus.FINISHED) {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_FARMER.name()))) {
                throw new AccessDeniedException("Only farmers are allowed to confirmed that the spraying session is finished.");
            }
            if (Long.parseLong(userDetails.getUsername()) != order.getFarmerId()) {
                throw new AccessDeniedException("You are not the farmer associated with this order. You are not allowed to mark it as finished.");
            }
            if (order.getStatus() != OrderStatus.ASSIGNED) {
                throw new IllegalArgumentException("The status of this order is " + order.getStatus().name() + ". You can only start an assigned orders.");
            }
        }

        if(dto.getStatus() == OrderStatus.COMPLETED) {
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

        order.setStatus(dto.getStatus());
        return orderRepository.save(order);
    }

    @Override
    public OrderDTO assignSprayer(Long id, AssignSprayerRequestDTO dto) throws Exception {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));
        order.setAssignedSprayerIds(dto.getSprayerIds());
        order.setStatus(OrderStatus.ASSIGNED);
        return orderRepository.save(order);
    }


}
