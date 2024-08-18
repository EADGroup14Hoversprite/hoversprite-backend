package enterprise.hoversprite.modules.auth.dtos.response;

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
