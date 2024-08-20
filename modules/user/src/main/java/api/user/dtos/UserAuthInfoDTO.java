package api.user.dtos;

import org.springframework.security.core.userdetails.UserDetails;
import shared.enums.AuthRole;
import shared.enums.UserRole;

public interface UserAuthInfoDTO extends UserDetails {
    Long getId();

    String getPassword();

    AuthRole getAuthRole();

    UserRole getUserRole();
}
