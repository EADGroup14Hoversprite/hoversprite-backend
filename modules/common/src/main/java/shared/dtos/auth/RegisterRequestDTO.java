package shared.dtos.auth;

import shared.enums.Expertise;
import shared.enums.UserRole;
import shared.types.Location;

public interface RegisterRequestDTO {
    String getFullName();

    String getPhoneNumber();

    String getEmailAddress();

    Location getHomeAddress();

    UserRole getUserRole();

    Expertise getExpertise();

    String getUsername();

    String getPassword();
}
