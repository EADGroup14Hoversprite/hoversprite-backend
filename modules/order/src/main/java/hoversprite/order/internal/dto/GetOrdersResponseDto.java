package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.order.external.dto.OrderDto;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrdersResponseDto {
    private String message;

    private List<OrderDto> orders;

    private Integer maxPage;
}
