package internal.dtos;

import shared.dtos.auth.SignInRequestDTO;
import lombok.Data;

@Data
public class SignInRequestDTOImpl implements SignInRequestDTO {

    private String emailOrPhone;

    private String password;
}
