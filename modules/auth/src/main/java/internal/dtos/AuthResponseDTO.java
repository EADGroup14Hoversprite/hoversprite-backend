package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String message;

    private String token;
}
