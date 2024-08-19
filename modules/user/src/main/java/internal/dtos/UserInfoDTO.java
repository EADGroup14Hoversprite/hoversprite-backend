package internal.dtos;

import api.user.dtos.IUserInfoDTO;
import lombok.AllArgsConstructor;
import shared.enums.Expertise;
import lombok.Data;
import shared.types.Location;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserInfoDTO implements IUserInfoDTO {
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String emailAddress;

    private Location homeAddress;

    private Boolean emailConfirmed;

    private Expertise expertise;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
