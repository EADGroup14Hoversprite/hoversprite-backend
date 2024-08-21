package internal.dtos;

import shared.dtos.user.UserAuthInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import shared.enums.AuthRole;
import shared.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class UserAuthInfoDTOImpl implements UserAuthInfoDTO {
    private Long id;

    private String password;

    private UserRole userRole;

    private AuthRole authRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authRole.name()), new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

}
