package enterprise.hoversprite.modules.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    @NonNull
    private String message;

    private String token;
}
