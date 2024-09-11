package hoversprite.auth.external.service;

import hoversprite.common.external.enums.AuthRole;
import hoversprite.common.external.enums.UserRole;
import hoversprite.user.external.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    Boolean isValid(String token, UserDetails user);

    String generateToken(UserDto dto);

    String getUserIdFromJwt(String token);

    UserRole getUserRoleFromJwt(String token);

    AuthRole getAuthRoleFromJwt(String token);

    Boolean isTokenExpired(String token);
}
