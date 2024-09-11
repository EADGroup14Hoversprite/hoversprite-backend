package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.order.external.dto.OrderDto;

import java.util.List;

@Data
@AllArgsConstructor
public class GetOrdersByFarmerIdResponseDto {

    private String message;

    private List<OrderDto> orders;
}