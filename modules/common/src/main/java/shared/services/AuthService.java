package shared.services;

import shared.dtos.auth.RegisterRequestDTO;
import shared.dtos.auth.SignInRequestDTO;

public interface AuthService {
    String register(RegisterRequestDTO dto) throws Exception;

    String signIn(SignInRequestDTO dto) throws Exception;

    Boolean emailConfirmation(String id);

    Boolean passwordRecovery(String id);
}
