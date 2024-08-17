package enterprise.hoversprite.common.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class ErrorResponseDTO {

    @NonNull
    private String message;
}
