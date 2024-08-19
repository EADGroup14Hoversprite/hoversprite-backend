package api.user;

import api.user.dtos.IUserAuthInfoDTO;
import api.user.dtos.IUserInfoDTO;
import shared.dtos.request.RegisterRequestDTO;

public interface IUserService {
    IUserAuthInfoDTO createUser(RegisterRequestDTO dto) throws Exception;

    IUserInfoDTO getUserInfoById(Long id) throws Exception;

    IUserAuthInfoDTO getUserAuthInfoById(Long id) throws Exception;

    IUserAuthInfoDTO getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;
}
