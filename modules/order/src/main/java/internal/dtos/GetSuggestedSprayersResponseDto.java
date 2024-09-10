package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.UserDto;

import java.util.List;

@Data
@AllArgsConstructor
public class GetSuggestedSprayersResponseDto {
    private String message;

    private List<UserDto> sprayers;
}
