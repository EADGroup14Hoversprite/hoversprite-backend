package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
}
