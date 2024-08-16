package enterprise.hoversprite.modules.order.service;

import enterprise.hoversprite.modules.order.model.Order;

import java.util.Optional;

public interface IOrderService {
    public Order saveOrder(Order order);

    public Optional<Order> getOrderById(Long id);

    public Order[] getAllOrders();

    public Optional<Order[]> getOrdersByFarmerId(Long id);

    public void deleteOrder(Order order);
}
