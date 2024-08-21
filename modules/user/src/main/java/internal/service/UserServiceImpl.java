package internal.service;

import internal.dtos.UserAuthInfoDTOImpl;
import internal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import shared.dtos.auth.RegisterRequestDTO;
import internal.model.User;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import shared.dtos.user.UserDTO;
import shared.enums.AuthRole;
import shared.enums.UserRole;
import shared.services.UserService;

import java.sql.SQLException;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserAuthInfoDTOImpl createUser(RegisterRequestDTO dto) throws Exception {
        if (userRepository.findByEmailAddress(dto.getEmailAddress()).isPresent()) {
            throw new DataIntegrityViolationException("This email has already been registered");
        }

        if (userRepository.findByPhoneNumber(dto.getPhoneNumber()).isPresent()) {
            throw new DataIntegrityViolationException("This phone number has already been registered");
        }

        if (!dto.getUserRole().equals(UserRole.ROLE_SPRAYER) && dto.getExpertise() != null) {
            throw new BadRequestException("Only sprayer can register with an expertise level. Farmers and receptionist must omit or leave the expertise field null.");
        }

        User user = new User(null, dto.getFullName(), dto.getPhoneNumber(), dto.getEmailAddress(), dto.getHomeAddress(), dto.getUserRole(), dto.getUsername(), dto.getPassword(), dto.getExpertise(), AuthRole.ROLE_USER, null, null);

        return userRepository.save(user).toUserAuthInfoDto();
    }

    @Override
    public UserDTO getUserInfoById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id does not exists"));
    }

    @Override
    public UserAuthInfoDTOImpl getUserAuthInfoById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id does not exists"));
        return user.toUserAuthInfoDto();
    }

    @Override
    public UserAuthInfoDTOImpl getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception {
        User user = userRepository.findByEmailAddress(emailOrPhone)
                .or(() -> userRepository.findByPhoneNumber(emailOrPhone))
                .orElseThrow(() -> new EntityNotFoundException("User with this email address or phone number does not exists"));
        return user.toUserAuthInfoDto();
    }

}
