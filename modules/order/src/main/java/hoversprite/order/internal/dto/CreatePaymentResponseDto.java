package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePaymentResponseDto {
    private String approvalUrl;
}
