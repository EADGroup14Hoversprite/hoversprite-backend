package enterprise.hoversprite.modules.auth.service;

import enterprise.hoversprite.modules.auth.dtos.SignInDTO;
import enterprise.hoversprite.modules.user.model.User;

public interface IAuthService {
    public String register(User user);
    public String signIn(SignInDTO dto);
    public boolean emailConfirmation(String id);
    public boolean passwordRecovery(String id);
}
