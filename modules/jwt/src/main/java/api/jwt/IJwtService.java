package api.jwt;

import api.user.dtos.IUserAuthInfoDTO;
import shared.enums.AuthRole;
import shared.enums.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(IUserAuthInfoDTO dto);

    String getUserIdFromJwt(String token);

    UserRole getUserRoleFromJwt(String token);

    AuthRole getAuthRoleFromJwt(String token);

    Boolean isValid(String token, UserDetails user);
}
