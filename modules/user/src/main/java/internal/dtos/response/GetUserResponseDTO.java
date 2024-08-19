package internal.dtos.response;

import internal.dtos.UserInfoDTO;
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
