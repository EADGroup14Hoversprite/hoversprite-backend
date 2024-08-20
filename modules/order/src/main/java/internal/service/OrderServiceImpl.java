package internal.service;

import api.order.OrderService;
import api.order.dtos.CreateOrderRequestDTO;
import api.order.dtos.OrderInfoDTO;
import internal.dtos.request.CreateOrderRequestDTOImpl;
import internal.model.Order;
import internal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderInfoDTO createOrder(CreateOrderRequestDTO dto) {
        return null;
    }

    @Override
    public OrderInfoDTO getOrderById(Long id) {
        return null;
    }
}
