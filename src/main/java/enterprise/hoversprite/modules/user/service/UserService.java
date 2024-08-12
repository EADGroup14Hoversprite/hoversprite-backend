package enterprise.hoversprite.modules.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import enterprise.hoversprite.modules.user.model.User;
import enterprise.hoversprite.modules.user.repository.UserRepository;

@Service
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  public User register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User findByPhoneNumber(String phoneNumber) {
    return userRepository.findByPhoneNumber(phoneNumber);
  }

  public boolean confirmEmail(String token) {
    // Logic to confirm email using the token
    User user = userRepository.findByEmailConfirmationToken(token);
    if (user != null) {
      user.setEmailConfirmed(true);
      userRepository.save(user);
      return true;
    }
    return false;
  }

  public boolean sendRecoveryEmail(String email) {
    User user = findByEmail(email);
    if (user != null) {
      // Logic to send recovery email
      String token = generateRecoveryToken(user);
      sendEmail(user.getEmailAddress(), token);
      return true;
    }
    return false;
  }

  private String generateRecoveryToken(User user) {
    // Generate a token for password recovery
    return "Generate Token";
  }

  private void sendEmail(String email, String token) {
    // Logic to send email
  }

  public User saveUser(User user) {
    // Encrypt password here
    return userRepository.save(user);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
