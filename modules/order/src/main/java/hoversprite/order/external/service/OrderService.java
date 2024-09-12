package hoversprite.order.external.service;


import hoversprite.order.external.dto.OrderDto;
import hoversprite.common.external.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderDto getOrderById(Long id) throws Exception;

    OrderDto updateOrderStatus(Long id, OrderStatus status) throws Exception;

    OrderDto assignSprayer(Long id, List<Long> sprayerIds) throws Exception;
}
