package enterprise.hoversprite.modules.auth.service;

import enterprise.hoversprite.modules.auth.dtos.SignInRequestDTO;
import enterprise.hoversprite.modules.user.model.User;

public interface IAuthService {
    String register(User user) throws Exception;

    String signIn(SignInRequestDTO dto) throws Exception;

    Boolean emailConfirmation(String id);

    Boolean passwordRecovery(String id);
}
