package hoversprite.auth.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuthUrlResponseDto {
    private String redirectUri;
}
