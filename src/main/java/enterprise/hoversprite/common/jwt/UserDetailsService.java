package enterprise.hoversprite.common.jwt;

import enterprise.hoversprite.modules.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            return userService.getUserById(Long.parseLong(id));
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}