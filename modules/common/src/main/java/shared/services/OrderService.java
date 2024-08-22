package shared.services;


import shared.dtos.order.AssignSprayerRequestDTO;
import shared.dtos.order.CreateOrderRequestDTO;
import shared.dtos.order.OrderDTO;
import shared.dtos.order.UpdateOrderStatusRequestDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(CreateOrderRequestDTO dto) throws Exception;

    OrderDTO getOrderById(Long id) throws Exception;

    List<OrderDTO> getOrdersByFarmerId() throws Exception;

    OrderDTO updateOrderStatus(Long id, UpdateOrderStatusRequestDTO dto) throws Exception;

    OrderDTO assignSprayer(Long id, AssignSprayerRequestDTO dto) throws Exception;
}
