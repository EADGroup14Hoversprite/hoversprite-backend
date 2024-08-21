package shared.services;

import org.springframework.security.core.userdetails.UserDetails;
import shared.enums.AuthRole;
import shared.enums.UserRole;
import shared.dtos.user.UserAuthInfoDTO;

public interface JwtService {
    String generateToken(UserAuthInfoDTO dto);

    String getUserIdFromJwt(String token);

    UserRole getUserRoleFromJwt(String token);

    AuthRole getAuthRoleFromJwt(String token);

    Boolean isValid(String token, UserDetails user);
}
