package hoversprite.order.internal.dto;

import lombok.Data;
import hoversprite.common.external.enums.OrderStatus;

@Data
public class UpdateOrderStatusRequestDto {
    private OrderStatus status;
}
