package internal.service;

import internal.model.Order;
import internal.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import shared.constants.Constants;
import shared.enums.OrderStatus;
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
    public OrderDTO createOrder(CreateOrderRequestDTO dto) throws Exception {
        int sessionNum = 1;
        Long numOrders = orderRepository.countByDesiredDateAndTimeSlot(dto.getDesiredDate(), dto.getTimeSlot());

        if (numOrders == 2) {
            return null;
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
    public OrderDTO getOrderById(Long id) throws Exception {
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
    public OrderDTO confirmOrder(Long id) throws Exception {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order with this id does not exist");
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with this id does not exist"));
        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }


}
