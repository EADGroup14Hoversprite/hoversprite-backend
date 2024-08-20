package api.user.dtos;

import shared.enums.Expertise;
import shared.types.Location;

import java.time.LocalDate;

public interface UserInfoDTO {

    Long getId();

    String getFullName();

    String getPhoneNumber();

    String getEmailAddress();

    Location getHomeAddress();

    Expertise getExpertise();

    LocalDate getCreatedAt();

    LocalDate getUpdatedAt();
}
