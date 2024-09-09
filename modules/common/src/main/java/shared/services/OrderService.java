package shared.services;


import shared.dtos.OrderDto;
import shared.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderDto getOrderById(Long id) throws Exception;

    List<OrderDto> getOrdersByFarmerId() throws Exception;

    OrderDto updateOrderStatus(Long id, OrderStatus status) throws Exception;

    OrderDto assignSprayer(Long id, List<Long> sprayerIds) throws Exception;
}
