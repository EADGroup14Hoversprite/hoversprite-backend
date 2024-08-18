package interfaces;

import dtos.request.RegisterRequestDTO;
import dtos.UserAuthInfoDTO;
import dtos.UserInfoDTO;

public interface IUserService {
    UserAuthInfoDTO createUser(RegisterRequestDTO dto) throws Exception;

    UserInfoDTO getUserInfoById(Long id) throws Exception;

    UserAuthInfoDTO getUserAuthInfoById(Long id) throws Exception;

    UserAuthInfoDTO getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;
}
