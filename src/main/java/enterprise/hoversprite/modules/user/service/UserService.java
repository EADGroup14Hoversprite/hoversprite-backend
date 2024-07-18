package enterprise.hoversprite.modules.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import enterprise.hoversprite.modules.user.model.User;
import enterprise.hoversprite.modules.user.repository.UserRepository;

@Service
public class UserService implements IUserService {
  @Autowired
  UserRepository userRepository;

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

  public boolean deleteUser(Long id) {
    userRepository.deleteById(id);
    return true;
  }
}
