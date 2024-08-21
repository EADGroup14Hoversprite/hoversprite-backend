package shared.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private String message;
}
