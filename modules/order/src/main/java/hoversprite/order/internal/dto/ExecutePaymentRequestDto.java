package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecutePaymentRequestDto {
    private String paymentId;

    private String payerId;
}
