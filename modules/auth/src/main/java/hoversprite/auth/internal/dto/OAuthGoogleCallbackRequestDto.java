package hoversprite.auth.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuthGoogleCallbackRequestDto {
    private String code;

    private String redirectUri;
}
