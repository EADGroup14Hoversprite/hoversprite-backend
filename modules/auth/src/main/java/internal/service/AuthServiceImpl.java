package internal.service;

import internal.dtos.AuthDTOImpl;
import shared.dtos.auth.RegisterRequestDTO;
import shared.dtos.auth.SignInRequestDTO;
import shared.dtos.user.UserDTO;
import shared.services.AuthService;
import shared.services.JwtService;
import shared.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import internal.dtos.RegisterRequestDTOImpl;

@Service
class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthDTOImpl register(RegisterRequestDTO dto) throws Exception {

        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        RegisterRequestDTOImpl newDto = new RegisterRequestDTOImpl(dto.getFullName(), dto.getPhoneNumber(), dto.getEmailAddress(), dto.getHomeAddress(), dto.getUserRole(), dto.getExpertise(), dto.getUsername(), encryptedPassword);
        UserDTO newUserDto = userService.createUser(newDto);

        return new AuthDTOImpl(newUserDto.getId(), newUserDto.getFullName(), newUserDto.getPhoneNumber(), newUserDto.getEmailAddress(), newUserDto.getHomeAddress(), newUserDto.getExpertise(), newUserDto.getCreatedAt(), newUserDto.getUpdatedAt(), jwtService.generateToken(newUserDto));
    }

    @Override
    public AuthDTOImpl signIn(SignInRequestDTO dto) throws Exception {

        UserDTO userDto = userService.getUserByEmailAddressOrPhoneNumber(dto.getEmailOrPhone());

        if (!passwordEncoder.matches(dto.getPassword(), userDto.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return new AuthDTOImpl(userDto.getId(), userDto.getFullName(), userDto.getPhoneNumber(), userDto.getEmailAddress(), userDto.getHomeAddress(), userDto.getExpertise(), userDto.getCreatedAt(), userDto.getUpdatedAt(), jwtService.generateToken(userDto));
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
