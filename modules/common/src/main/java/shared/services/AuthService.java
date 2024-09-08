package shared.services;

import shared.dtos.auth.AuthDTO;
import shared.dtos.auth.RegisterRequestDTO;
import shared.dtos.auth.SignInRequestDTO;

public interface AuthService {
    AuthDTO register(RegisterRequestDTO dto) throws Exception;

    AuthDTO signIn(SignInRequestDTO dto) throws Exception;

    Boolean emailConfirmation(String id);

    Boolean passwordRecovery(String id);
}
