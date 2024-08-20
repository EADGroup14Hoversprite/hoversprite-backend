package internal.dtos;

import api.user.dtos.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class GetUserResponseDTO {
    private String message;

    private UserInfoDTO userInfoDTO;
}
