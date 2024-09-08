package shared.services;


import shared.dtos.auth.RegisterRequestDTO;
import shared.dtos.user.UserDTO;

public interface UserService {
    UserDTO createUser(RegisterRequestDTO dto) throws Exception;

    UserDTO getUserById(Long id) throws Exception;

    UserDTO getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;
}
