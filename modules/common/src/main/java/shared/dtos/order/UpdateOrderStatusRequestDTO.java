package shared.dtos.order;

import shared.enums.OrderStatus;

public interface UpdateOrderStatusRequestDTO {
    OrderStatus getStatus();
}
