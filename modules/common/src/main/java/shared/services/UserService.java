package shared.services;


import shared.dtos.UserDto;
import shared.enums.Expertise;
import shared.enums.UserRole;
import shared.types.Location;

public interface UserService {
    UserDto createUser(String fullName, String phoneNumber, String emailAddress, UserRole userRole, Expertise expertise, String username, String password) throws Exception;

    UserDto getUserById(Long id) throws Exception;

    UserDto getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;
}
