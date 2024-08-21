package shared.services;


import shared.dtos.order.CreateOrderRequestDTO;
import shared.dtos.order.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(CreateOrderRequestDTO dto) throws Exception;

    OrderDTO getOrderById(Long id) throws Exception;

    List<OrderDTO> getOrdersByFarmerId() throws Exception;

    OrderDTO confirmOrder(Long id) throws Exception;
}
