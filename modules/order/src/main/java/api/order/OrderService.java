package api.order;

import api.order.dtos.CreateOrderRequestDTO;
import api.order.dtos.OrderInfoDTO;

public interface OrderService {
    OrderInfoDTO createOrder(CreateOrderRequestDTO dto);

    OrderInfoDTO getOrderById(Long id);
}
