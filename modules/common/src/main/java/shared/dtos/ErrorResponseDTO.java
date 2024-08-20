package shared.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
public class ErrorResponseDTO {

    @NonNull
    private String message;

    public ErrorResponseDTO(@NonNull String message) {
        this.message = message;
    }
}
