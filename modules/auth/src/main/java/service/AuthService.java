package service;

import dtos.UserAuthInfoDTO;
import dtos.request.RegisterRequestDTO;
import dtos.request.SignInRequestDTO;
import interfaces.IAuthService;
import interfaces.IJwtService;
import interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class AuthService implements IAuthService {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IJwtService jwtService;

    @Override
    public String register(RegisterRequestDTO dto) throws Exception {

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        return jwtService.generateToken(userService.createUser(dto));
    }

    @Override
    public String signIn(SignInRequestDTO dto) throws Exception {

        UserAuthInfoDTO authDto = userService.getUserByEmailAddressOrPhoneNumber(dto.getEmailOrPhone());

        if (!passwordEncoder.matches(dto.getPassword(), authDto.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return jwtService.generateToken(authDto);
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
