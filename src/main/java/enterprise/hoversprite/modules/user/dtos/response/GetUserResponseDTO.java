package enterprise.hoversprite.modules.user.dtos.response;

import enterprise.hoversprite.modules.user.dtos.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class GetUserResponseDTO {
    @NonNull
    private String message;

    private UserInfoDTO userInfo;

}
