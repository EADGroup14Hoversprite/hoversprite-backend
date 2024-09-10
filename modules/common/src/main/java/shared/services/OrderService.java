package shared.services;


import shared.dtos.OrderDto;
import shared.dtos.UserDto;
import shared.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderDto getOrderById(Long id) throws Exception;

    List<OrderDto> getOrdersByFarmerId() throws Exception;

    OrderDto updateOrderStatus(Long id, OrderStatus status) throws Exception;

    OrderDto assignSprayer(Long id, List<Long> sprayerIds) throws Exception;

    List<UserDto> getSuggestedSprayers(Long id, LocalDate startDate, LocalDate endDate) throws Exception;
}
