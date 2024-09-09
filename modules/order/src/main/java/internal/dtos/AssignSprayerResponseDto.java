package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.OrderDto;

@Data
@AllArgsConstructor
public class AssignSprayerResponseDto {

    private String message;

    private OrderDto order;
}
