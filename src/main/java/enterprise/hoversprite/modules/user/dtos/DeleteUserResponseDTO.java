package enterprise.hoversprite.modules.user.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class DeleteUserResponseDTO {
    @NonNull
    private String message;
}
