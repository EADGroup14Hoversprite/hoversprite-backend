package internal.dtos;

import shared.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserResponseDTO {
    private String message;

    private UserDTO userInfoDTO;
}
