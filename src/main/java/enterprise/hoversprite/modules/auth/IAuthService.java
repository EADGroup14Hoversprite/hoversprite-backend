package enterprise.hoversprite.modules.auth;

import enterprise.hoversprite.modules.auth.dtos.request.RegisterRequestDTO;
import enterprise.hoversprite.modules.auth.dtos.request.SignInRequestDTO;

public interface IAuthService {
    String register(RegisterRequestDTO dto) throws Exception;

    String signIn(SignInRequestDTO dto) throws Exception;

    Boolean emailConfirmation(String id);

    Boolean passwordRecovery(String id);
}
