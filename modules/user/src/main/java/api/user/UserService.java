package api.user;

import api.user.dtos.UserAuthInfoDTO;
import api.user.dtos.UserInfoDTO;
import shared.dtos.RegisterRequestDTO;

public interface UserService {
    UserAuthInfoDTO createUser(RegisterRequestDTO dto) throws Exception;

    UserInfoDTO getUserInfoById(Long id) throws Exception;

    UserAuthInfoDTO getUserAuthInfoById(Long id) throws Exception;

    UserAuthInfoDTO getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;
}
