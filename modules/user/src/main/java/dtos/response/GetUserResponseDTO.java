package dtos.response;

import dtos.UserInfoDTO;
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
