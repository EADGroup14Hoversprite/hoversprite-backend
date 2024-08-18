package dtos;

import enums.Expertise;
import lombok.AllArgsConstructor;
import lombok.Data;
import types.Location;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserInfoDTO {
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
