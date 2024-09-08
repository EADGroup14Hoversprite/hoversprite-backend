package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.auth.AuthDTO;
import shared.dtos.user.UserDTO;
import shared.enums.Expertise;
import shared.types.Location;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class AuthDTOImpl implements AuthDTO {

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
