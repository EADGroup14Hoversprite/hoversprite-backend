package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.order.OrderDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class GetOrdersByFarmerIdResponseDTO {

    private String message;

    private List<OrderDTO> orders;
}
