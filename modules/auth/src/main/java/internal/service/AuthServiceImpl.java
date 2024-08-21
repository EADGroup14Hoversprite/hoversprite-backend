package internal.service;

import shared.dtos.auth.RegisterRequestDTO;
import shared.dtos.auth.SignInRequestDTO;
import shared.services.AuthService;
import shared.services.JwtService;
import shared.dtos.user.UserAuthInfoDTO;
import shared.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import internal.dtos.RegisterRequestDTOImpl;
import internal.dtos.SignInRequestDTOImpl;

@Service
class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public String register(RegisterRequestDTO dto) throws Exception {

        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        RegisterRequestDTOImpl newDto = new RegisterRequestDTOImpl(dto.getFullName(), dto.getPhoneNumber(), dto.getEmailAddress(), dto.getHomeAddress(), dto.getUserRole(), dto.getExpertise(), dto.getUsername(), encryptedPassword);

        return jwtService.generateToken(userService.createUser(newDto));
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
