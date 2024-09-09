package shared.services;

import org.springframework.security.core.userdetails.UserDetails;
import shared.dtos.UserDto;
import shared.enums.AuthRole;
import shared.enums.UserRole;

public interface JwtService {
    String generateToken(UserDto dto);

    String getUserIdFromJwt(String token);

    UserRole getUserRoleFromJwt(String token);

    AuthRole getAuthRoleFromJwt(String token);

    Boolean isValid(String token, UserDetails user);
}
