package hoversprite.auth.internal.dto;

import lombok.Data;

@Data
public class SignInRequestDTO {

    private String emailOrPhone;

    private String password;
}
