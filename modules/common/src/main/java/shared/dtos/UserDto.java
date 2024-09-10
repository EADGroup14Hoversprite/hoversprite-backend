package shared.dtos;

import org.springframework.security.core.userdetails.UserDetails;
import shared.enums.AuthRole;
import shared.enums.Expertise;
import shared.enums.UserRole;

import java.time.LocalDate;

public interface UserDto extends UserDetails {

    Long getId();

    String getFullName();

    String getPhoneNumber();

    String getEmailAddress();

    Expertise getExpertise();

    LocalDate getCreatedAt();

    LocalDate getUpdatedAt();

    UserRole getUserRole();

    AuthRole getAuthRole();
}
