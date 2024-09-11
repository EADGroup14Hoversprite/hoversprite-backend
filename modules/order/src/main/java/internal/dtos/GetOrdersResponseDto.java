package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.OrderDto;

import java.util.List;

@Data
@AllArgsConstructor
public class GetOrdersResponseDto {
    private String message;

    private List<OrderDto> orders;
}
