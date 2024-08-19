package api.order;

import internal.dtos.OrderInfoDTO;
import shared.dtos.request.CreateOrderRequestDTO;

public interface IOrderService {
    OrderInfoDTO createOrder(CreateOrderRequestDTO dto);

    OrderInfoDTO getOrderById(Long id);
}
