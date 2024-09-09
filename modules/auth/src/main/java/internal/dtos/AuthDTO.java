package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.enums.Expertise;
import shared.types.Location;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthDTO {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String emailAddress;

    private Location homeAddress;

    private Expertise expertise;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String accessToken;

}
