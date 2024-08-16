package enterprise.hoversprite.modules.auth.service;

import enterprise.hoversprite.common.jwt.JwtService;
import enterprise.hoversprite.modules.auth.dtos.SignInRequestDTO;
import enterprise.hoversprite.modules.user.model.User;
import enterprise.hoversprite.modules.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public String register(User user) throws Exception {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return jwtService.generateToken(userService.createUser(user));
    }

    @Override
    public String signIn(SignInRequestDTO dto) throws Exception {

        User user = userService.getUserByEmailAddressOrPhoneNumber(dto.getEmailOrPhone());

        return jwtService.generateToken(user);
    }

    @Override
    public Boolean emailConfirmation(String id) {
        return false;
    }

    @Override
    public Boolean passwordRecovery(String id) {
        return false;
    }
}
