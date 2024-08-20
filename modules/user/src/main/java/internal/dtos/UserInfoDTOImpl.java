package internal.dtos;

import api.user.dtos.UserInfoDTO;
import lombok.AllArgsConstructor;
import shared.enums.Expertise;
import lombok.Data;
import shared.types.Location;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserInfoDTOImpl implements UserInfoDTO {
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String emailAddress;

    private Location homeAddress;

    private Expertise expertise;

    private LocalDate createdAt;

    private LocalDate updatedAt;

}