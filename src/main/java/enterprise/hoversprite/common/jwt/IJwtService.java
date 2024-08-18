package enterprise.hoversprite.common.jwt;

import enterprise.hoversprite.common.enums.AuthRole;
import enterprise.hoversprite.common.enums.UserRole;
import enterprise.hoversprite.modules.user.dtos.UserAuthInfoDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    public String generateToken(UserAuthInfoDTO dto);

    public String getUserIdFromJwt(String token);

    public UserRole getUserRoleFromJwt(String token);

    public AuthRole getAuthRoleFromJwt(String token);

    public Boolean isValid(String token, UserDetails user);
}
