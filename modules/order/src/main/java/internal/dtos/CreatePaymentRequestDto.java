package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePaymentRequestDto {
    private String successUrl;

    private String cancelUrl;
}
