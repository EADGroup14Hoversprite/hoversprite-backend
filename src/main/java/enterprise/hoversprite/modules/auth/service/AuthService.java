package enterprise.hoversprite.modules.auth.service;

import enterprise.hoversprite.modules.auth.dtos.SignInDTO;
import enterprise.hoversprite.modules.auth.repository.AuthRepository;
import enterprise.hoversprite.modules.user.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public String register(User user) {
        return "";
    }

    @Override
    public String signIn(SignInDTO dto) {
        return "";
    }

    @Override
    public boolean emailConfirmation(String id) {
        return false;
    }

    @Override
    public boolean passwordRecovery(String id) {
        return false;
    }
}
