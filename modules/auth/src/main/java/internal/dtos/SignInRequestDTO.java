package internal.dtos;

import lombok.Data;

@Data
public class SignInRequestDTO {

    private String emailOrPhone;

    private String password;
}
