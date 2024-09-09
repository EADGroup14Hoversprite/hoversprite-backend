package internal.dtos;

import shared.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserResponseDto {
    private String message;

    private UserDto userInfoDTO;
}
