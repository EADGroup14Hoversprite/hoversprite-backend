package enterprise.hoversprite.modules.user;

import enterprise.hoversprite.modules.auth.dtos.request.RegisterRequestDTO;
import enterprise.hoversprite.modules.user.dtos.UserAuthInfoDTO;
import enterprise.hoversprite.modules.user.dtos.UserInfoDTO;

public interface IUserService {
    UserAuthInfoDTO createUser(RegisterRequestDTO dto) throws Exception;

    UserInfoDTO getUserInfoById(Long id) throws Exception;

    UserAuthInfoDTO getUserAuthInfoById(Long id) throws Exception;

    UserAuthInfoDTO getUserByEmailAddressOrPhoneNumber(String emailOrPhone) throws Exception;
}
