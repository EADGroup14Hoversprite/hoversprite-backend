package internal.dtos;

import lombok.Data;
import shared.dtos.order.UpdateOrderStatusRequestDTO;
import shared.enums.OrderStatus;

@Data
public class UpdateOrderStatusRequestDTOImpl implements UpdateOrderStatusRequestDTO {
    private OrderStatus status;
}
