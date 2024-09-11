package hoversprite.user.internal.dto;

import hoversprite.user.external.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserResponseDto {
    private String message;

    private UserDto userInfoDTO;
}
