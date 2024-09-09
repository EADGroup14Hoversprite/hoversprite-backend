package shared.services;


import shared.dtos.OrderDto;
import shared.enums.CropType;
import shared.enums.OrderSlot;
import shared.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderDto createOrder(Long farmerId, CropType cropType, Float farmlandArea, LocalDate desiredDate, OrderSlot timeSlot) throws Exception;

    OrderDto getOrderById(Long id) throws Exception;

    List<OrderDto> getOrdersByFarmerId() throws Exception;

    OrderDto updateOrderStatus(Long id, OrderStatus status) throws Exception;

    OrderDto assignSprayer(Long id, List<Long> sprayerIds) throws Exception;
}
