package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.OrderDto;

import java.util.List;

@Data
@AllArgsConstructor
public class GetOrdersByFarmerIdResponseDto {

    private String message;

    private List<OrderDto> orders;
}
