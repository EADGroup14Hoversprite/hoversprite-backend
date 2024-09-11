package hoversprite.user.external.service;


import hoversprite.user.external.dto.UserDto;
import hoversprite.common.external.enums.Expertise;
import hoversprite.common.external.enums.UserRole;

import java.util.List;

public interface UserService {
    UserDto createUser(String fullName, String phoneNumber, String emailAddress, UserRole userRole, Expertise expertise, String username, String password) throws Exception;

    UserDto getUserById(Long id) throws Exception;

    UserDto getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;

    List<UserDto> getUsersByUserRole(UserRole userRole);
}
