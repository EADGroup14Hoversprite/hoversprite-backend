package enterprise.hoversprite.modules.order.service;

import enterprise.hoversprite.modules.order.model.Order;
import enterprise.hoversprite.modules.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        return null;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order[] getAllOrders() {
        return null;
    }

    @Override
    public Optional<Order[]> getOrdersByFarmerId(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteOrder(Order order) {
    }
}
