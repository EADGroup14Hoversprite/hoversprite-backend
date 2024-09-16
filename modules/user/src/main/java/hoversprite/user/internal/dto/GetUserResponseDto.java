package hoversprite.user.internal.dto;

import hoversprite.user.external.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponseDto {
    private String message;

    private UserDto userInfoDTO;
}
