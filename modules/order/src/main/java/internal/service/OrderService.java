package internal.service;

import api.order.IOrderService;
import internal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
}
