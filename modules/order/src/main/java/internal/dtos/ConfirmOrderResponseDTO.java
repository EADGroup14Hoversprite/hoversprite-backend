package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.order.OrderDTO;

@Data
@AllArgsConstructor
public class ConfirmOrderResponseDTO {

    private String message;

    private OrderDTO order;
}
