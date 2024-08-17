package enterprise.hoversprite.modules.user.service;

import enterprise.hoversprite.modules.user.model.User;
import enterprise.hoversprite.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) throws Exception {
        if (userRepository.findByEmailAddress(user.getEmailAddress()).isPresent()) {
            throw new SQLException("This email has already been registered");
        }

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new SQLException("This phone number has already been registered");
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User with this id does not exists"));
    }

    @Override
    public User getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception {
        return userRepository.findByEmailAddress(emailOrPhone)
                .or(() -> userRepository.findByPhoneNumber(emailOrPhone))
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User with this email address or phone number does not exists"));
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        if (!userRepository.existsById(id)) {
            throw new Exception("User with this id does not exists");
        }
        userRepository.deleteById(id);
    }
}
