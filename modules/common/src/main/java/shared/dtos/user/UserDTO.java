package shared.dtos.user;

import shared.enums.Expertise;
import shared.types.Location;

import java.time.LocalDate;

public interface UserDTO {

    Long getId();

    String getFullName();

    String getPhoneNumber();

    String getEmailAddress();

    Location getHomeAddress();

    Expertise getExpertise();

    LocalDate getCreatedAt();

    LocalDate getUpdatedAt();
}
