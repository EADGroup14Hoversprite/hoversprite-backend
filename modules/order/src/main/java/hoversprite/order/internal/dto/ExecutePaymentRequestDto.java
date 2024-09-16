package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutePaymentRequestDto {
    private String paymentId;

    private String payerId;
}
