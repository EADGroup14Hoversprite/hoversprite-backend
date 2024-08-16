package enterprise.hoversprite.modules.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class GetUserResponseDTO {
    @NonNull
    private String message;

    private UserDTO userDTO;

}
