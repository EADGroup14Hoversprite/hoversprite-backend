package enterprise.hoversprite.modules.order.controller;

import enterprise.hoversprite.modules.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

}
