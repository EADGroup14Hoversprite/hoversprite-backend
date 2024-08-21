package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.order.OrderDTO;

@Data
@AllArgsConstructor
public class GetOrderResponseDTO {
    private String message;

    private OrderDTO order;
}
