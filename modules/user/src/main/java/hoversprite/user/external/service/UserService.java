package hoversprite.user.external.service;


import hoversprite.common.external.type.Location;
import hoversprite.user.external.dto.UserDto;
import hoversprite.common.external.enums.Expertise;
import hoversprite.common.external.enums.UserRole;

import java.util.List;

public interface UserService {
    UserDto createUser(String fullName, String phoneNumber, String emailAddress, String homeAddress, Location location, UserRole userRole, String googleId, String facebookId, Expertise expertise, String password) throws Exception;

    UserDto getUserById(Long id) throws Exception;

    UserDto getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;

    List<UserDto> getUsersByUserRole(UserRole userRole);

    UserDto getUserByGoogleId(String id) throws Exception;

    UserDto getUserByFacebookId(String id) throws Exception;

}
