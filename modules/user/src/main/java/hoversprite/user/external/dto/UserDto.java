package hoversprite.user.external.dto;

import org.springframework.security.core.userdetails.UserDetails;
import hoversprite.common.external.enums.AuthRole;
import hoversprite.common.external.enums.Expertise;
import hoversprite.common.external.enums.UserRole;

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
