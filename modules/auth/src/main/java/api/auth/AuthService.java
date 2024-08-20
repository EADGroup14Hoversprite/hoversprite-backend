package api.auth;

import shared.dtos.RegisterRequestDTO;
import shared.dtos.SignInRequestDTO;

public interface AuthService {
    String register(RegisterRequestDTO dto) throws Exception;

    String signIn(SignInRequestDTO dto) throws Exception;

    Boolean emailConfirmation(String id);

    Boolean passwordRecovery(String id);
}
