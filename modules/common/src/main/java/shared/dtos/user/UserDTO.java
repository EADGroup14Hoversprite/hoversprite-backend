package shared.dtos.user;

import org.springframework.security.core.userdetails.UserDetails;
import shared.enums.AuthRole;
import shared.enums.Expertise;
import shared.enums.UserRole;
import shared.types.Location;

import java.time.LocalDate;

public interface UserDTO extends UserDetails {

    Long getId();

    String getFullName();

    String getPhoneNumber();

    String getEmailAddress();

    Location getHomeAddress();

    Expertise getExpertise();

    LocalDate getCreatedAt();

    LocalDate getUpdatedAt();

    UserRole getUserRole();

    AuthRole getAuthRole();
}
