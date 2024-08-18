package service;

import dtos.UserAuthInfoDTO;
import dtos.UserInfoDTO;
import dtos.request.RegisterRequestDTO;
import enums.UserRole;
import enums.AuthRole;
import interfaces.IUserService;
import model.User;
import repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserAuthInfoDTO createUser(RegisterRequestDTO dto) throws Exception {
        if (userRepository.findByEmailAddress(dto.getEmailAddress()).isPresent()) {
            throw new SQLException("This email has already been registered");
        }

        if (userRepository.findByPhoneNumber(dto.getPhoneNumber()).isPresent()) {
            throw new SQLException("This phone number has already been registered");
        }

        if (!dto.getUserRole().equals(UserRole.ROLE_SPRAYER) && dto.getExpertise() != null) {
            throw new BadRequestException("Only sprayer can register with an expertise level. Farmers and receptionist must omit or leave the expertise field null.");
        }

        User user = new User(null, dto.getFullName(), dto.getPhoneNumber(), dto.getEmailAddress(), dto.getHomeAddress(), dto.getUserRole(), dto.getUsername(), dto.getPassword(), false, dto.getExpertise(), AuthRole.ROLE_USER, null, null);

        return userRepository.save(user).toUserAuthInfoDto();
    }

    @Override
    public UserInfoDTO getUserInfoById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("internal.User with this id does not exists"));
        return user.toUserInfoDto();
    }

    @Override
    public UserAuthInfoDTO getUserAuthInfoById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("internal.User with this id does not exists"));
        return user.toUserAuthInfoDto();
    }

    @Override
    public UserAuthInfoDTO getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception {
        User user = userRepository.findByEmailAddress(emailOrPhone)
                .or(() -> userRepository.findByPhoneNumber(emailOrPhone))
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("internal.User with this email address or phone number does not exists"));
        return user.toUserAuthInfoDto();
    }

}
