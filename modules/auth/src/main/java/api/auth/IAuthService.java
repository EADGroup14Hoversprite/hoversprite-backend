package api.auth;

import shared.dtos.request.RegisterRequestDTO;
import shared.dtos.request.SignInRequestDTO;

public interface IAuthService {
    String register(RegisterRequestDTO dto) throws Exception;

    String signIn(SignInRequestDTO dto) throws Exception;

    Boolean emailConfirmation(String id);

    Boolean passwordRecovery(String id);
}
