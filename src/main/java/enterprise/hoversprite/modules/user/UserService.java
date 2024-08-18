package enterprise.hoversprite.modules.user;

import enterprise.hoversprite.common.enums.AuthRole;
import enterprise.hoversprite.common.enums.Expertise;
import enterprise.hoversprite.common.enums.UserRole;
import enterprise.hoversprite.modules.auth.dtos.request.RegisterRequestDTO;
import enterprise.hoversprite.modules.user.dtos.UserAuthInfoDTO;
import enterprise.hoversprite.modules.user.dtos.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
class UserService implements IUserService {

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

        User user = new User(null, dto.getFullName(), dto.getPhoneNumber(), dto.getEmailAddress(), dto.getHomeAddress(), UserRole.valueOf(dto.getUserRole()), dto.getUsername(), dto.getPassword(), false, (dto.getExpertise() == null ? null : Expertise.valueOf(dto.getExpertise())), AuthRole.ROLE_USER, null, null);

        return userRepository.save(user).toUserAuthInfoDto();
    }

    @Override
    public UserInfoDTO getUserInfoById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User with this id does not exists"));
        return user.toUserInfoDto();
    }

    @Override
    public UserAuthInfoDTO getUserAuthInfoById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User with this id does not exists"));
        return user.toUserAuthInfoDto();
    }

    @Override
    public UserAuthInfoDTO getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception {
        User user = userRepository.findByEmailAddress(emailOrPhone)
                .or(() -> userRepository.findByPhoneNumber(emailOrPhone))
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User with this email address or phone number does not exists"));
        return user.toUserAuthInfoDto();
    }

}
