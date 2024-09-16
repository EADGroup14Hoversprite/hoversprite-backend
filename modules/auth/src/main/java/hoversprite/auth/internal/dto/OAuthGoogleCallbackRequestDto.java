package hoversprite.auth.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthGoogleCallbackRequestDto {
    private String code;

    private String redirectUri;
}
