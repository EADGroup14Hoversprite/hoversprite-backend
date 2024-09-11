package hoversprite.auth.internal.service;

import hoversprite.auth.internal.dto.AuthDTO;
import hoversprite.auth.internal.dto.RegisterRequestDTO;
import hoversprite.auth.internal.dto.SignInRequestDTO;
import hoversprite.user.external.dto.UserDto;
import hoversprite.user.external.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthDTO register(RegisterRequestDTO dto) throws Exception {

        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        UserDto newUserDto = userService.createUser(dto.getFullName(), dto.getPhoneNumber(), dto.getEmailAddress(), dto.getUserRole(), dto.getExpertise(), dto.getUsername(), encryptedPassword);

        return new AuthDTO(newUserDto.getId(), newUserDto.getFullName(), newUserDto.getPhoneNumber(), newUserDto.getEmailAddress(), newUserDto.getExpertise(), newUserDto.getCreatedAt(), newUserDto.getUpdatedAt(), jwtService.generateToken(newUserDto));
    }

    public AuthDTO signIn(SignInRequestDTO dto) throws Exception {

        UserDto userDto = userService.getUserByEmailAddressOrPhoneNumber(dto.getEmailOrPhone());

        if (!passwordEncoder.matches(dto.getPassword(), userDto.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return new AuthDTO(userDto.getId(), userDto.getFullName(), userDto.getPhoneNumber(), userDto.getEmailAddress(), userDto.getExpertise(), userDto.getCreatedAt(), userDto.getUpdatedAt(), jwtService.generateToken(userDto));
    }
}
