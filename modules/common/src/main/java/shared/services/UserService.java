package shared.services;


import shared.dtos.auth.RegisterRequestDTO;
import shared.dtos.user.UserAuthInfoDTO;
import shared.dtos.user.UserDTO;

public interface UserService {
    UserAuthInfoDTO createUser(RegisterRequestDTO dto) throws Exception;

    UserDTO getUserInfoById(Long id) throws Exception;

    UserAuthInfoDTO getUserAuthInfoById(Long id) throws Exception;

    UserAuthInfoDTO getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;
}
