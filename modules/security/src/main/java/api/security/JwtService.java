package api.security;

import org.springframework.security.core.userdetails.UserDetails;
import shared.enums.AuthRole;
import shared.enums.UserRole;
import api.user.dtos.UserAuthInfoDTO;

public interface JwtService {
    String generateToken(UserAuthInfoDTO dto);

    String getUserIdFromJwt(String token);

    UserRole getUserRoleFromJwt(String token);

    AuthRole getAuthRoleFromJwt(String token);

    Boolean isValid(String token, UserDetails user);
}
