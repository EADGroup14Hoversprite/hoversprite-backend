package api.user.dtos;

import shared.enums.Expertise;
import shared.types.Location;

import java.time.LocalDate;

public interface IUserInfoDTO {

    Long getId();

    String getFullName();

    String getPhoneNumber();

    String getEmailAddress();

    Location getHomeAddress();

    Boolean getEmailConfirmed();

    Expertise getExpertise();

    LocalDate getCreatedAt();

    LocalDate getUpdatedAt();
}
