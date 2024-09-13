package hoversprite.auth.internal.dto;

import lombok.Data;

@Data
public class SignInRequestDto {

    private String emailOrPhone;

    private String password;
}
