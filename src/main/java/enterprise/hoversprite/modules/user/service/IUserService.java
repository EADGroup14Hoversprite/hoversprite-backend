package enterprise.hoversprite.modules.user.service;

import enterprise.hoversprite.modules.user.model.User;

import java.util.List;

public interface IUserService {
    User createUser(User user) throws Exception;

    List<User> getAllUsers();

    User getUserById(Long id) throws Exception;

    User getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;

    void deleteUser(Long id) throws Exception;
}
