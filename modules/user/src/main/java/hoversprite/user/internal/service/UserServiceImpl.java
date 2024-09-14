package hoversprite.user.internal.service;

import hoversprite.common.external.type.Location;
import hoversprite.user.internal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import hoversprite.user.internal.model.User;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hoversprite.user.external.dto.UserDto;
import hoversprite.common.external.enums.AuthRole;
import hoversprite.common.external.enums.Expertise;
import hoversprite.common.external.enums.UserRole;
import hoversprite.user.external.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(String fullName, String phoneNumber, String emailAddress, String homeAddress, Location location, UserRole userRole, String googleId, String facebookId,  Expertise expertise, String password) throws Exception {
        if (userRepository.findByEmailAddress(emailAddress).isPresent()) {
            throw new DataIntegrityViolationException("This email has already been registered");
        }

        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new DataIntegrityViolationException("This phone number has already been registered");
        }

        if (!userRole.equals(UserRole.ROLE_SPRAYER) && expertise != null) {
            throw new BadRequestException("Only sprayer can register with an expertise level. Farmers and receptionist must omit or leave the expertise field null.");
        } else if (userRole.equals(UserRole.ROLE_SPRAYER) && expertise == null) {
            throw new BadRequestException("Sprayer must be registered with an expertise level.");
        }

        User user = new User(null, fullName, phoneNumber, emailAddress, homeAddress, location, userRole, password, expertise, AuthRole.ROLE_USER, googleId, facebookId, null, null);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id does not exists"));
    }

    @Override
    public User getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws EntityNotFoundException {
        return userRepository.findByEmailAddress(emailOrPhone)
                .or(() -> userRepository.findByPhoneNumber(emailOrPhone))
                .orElseThrow(() -> new EntityNotFoundException("User with this email address or phone number does not exists"));
    }

    @Override
    public List<UserDto> getUsersByUserRole(UserRole userRole) {
        return userRepository.findByUserRole(userRole).stream().map(entity -> (UserDto) entity).toList();
    }

    @Override
    public UserDto getUserByGoogleId(String id) throws Exception {
        return userRepository.findByFacebookId(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this Facebook id does not exist."));
    }

    @Override
    public UserDto getUserByFacebookId(String id) throws Exception {
        return userRepository.findByGoogleId(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this Google id does not exist."));
    }

}
