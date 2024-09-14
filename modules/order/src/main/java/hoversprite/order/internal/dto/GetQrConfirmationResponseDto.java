package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetQrConfirmationResponseDto {
    private String message;

    private byte[] qrCode;
}
