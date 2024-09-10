package internal.service;

import internal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import internal.model.User;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shared.dtos.OrderDto;
import shared.dtos.UserDto;
import shared.enums.AuthRole;
import shared.enums.Expertise;
import shared.enums.UserRole;
import shared.services.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(String fullName, String phoneNumber, String emailAddress, UserRole userRole, Expertise expertise, String username, String password) throws Exception {
        if (userRepository.findByEmailAddress(emailAddress).isPresent()) {
            throw new DataIntegrityViolationException("This email has already been registered");
        }

        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new DataIntegrityViolationException("This phone number has already been registered");
        }

        if (!userRole.equals(UserRole.ROLE_SPRAYER) && expertise != null) {
            throw new BadRequestException("Only sprayer can register with an expertise level. Farmers and receptionist must omit or leave the expertise field null.");
        }

        User user = new User(null, fullName, phoneNumber, emailAddress, userRole, username, password, expertise, AuthRole.ROLE_USER, null, null);

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

}
