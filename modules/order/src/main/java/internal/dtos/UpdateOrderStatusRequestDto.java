package internal.dtos;

import lombok.Data;
import shared.enums.OrderStatus;

@Data
public class UpdateOrderStatusRequestDto {
    private OrderStatus status;
}
