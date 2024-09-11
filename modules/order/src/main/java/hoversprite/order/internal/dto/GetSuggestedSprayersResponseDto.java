package hoversprite.order.internal.dto;

import hoversprite.user.external.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GetSuggestedSprayersResponseDto {
    private String message;

    private List<UserDto> sprayers;
}
