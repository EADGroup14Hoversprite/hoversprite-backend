package hoversprite.auth.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignInRequestDto {

    private String emailOrPhone;

    private String password;
}
