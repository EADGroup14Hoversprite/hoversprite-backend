package enterprise.hoversprite.common.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class AuthErrorResponseDTO {

    @NonNull
    private String message;
}
