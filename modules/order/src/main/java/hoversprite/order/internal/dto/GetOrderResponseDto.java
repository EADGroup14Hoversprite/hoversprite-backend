package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.order.external.dto.OrderDto;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderResponseDto {
    private String message;

    private OrderDto order;
}
