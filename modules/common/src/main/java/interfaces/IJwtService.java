package interfaces;

import dtos.UserAuthInfoDTO;
import enums.AuthRole;
import enums.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(UserAuthInfoDTO dto);

    String getUserIdFromJwt(String token);

    UserRole getUserRoleFromJwt(String token);

    AuthRole getAuthRoleFromJwt(String token);

    Boolean isValid(String token, UserDetails user);
}
