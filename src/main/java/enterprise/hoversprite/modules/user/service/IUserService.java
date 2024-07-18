package enterprise.hoversprite.modules.user.service;

import java.util.List;
import java.util.Optional;

import enterprise.hoversprite.modules.user.model.User;

public interface IUserService {
  public User saveUser(User user);

  public List<User> getAllUsers();

  public Optional<User> getUserById(Long id);

  public boolean deleteUser(Long id);
}
