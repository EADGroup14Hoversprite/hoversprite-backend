package hoversprite.auth.internal.dto;

import hoversprite.common.external.type.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.common.external.enums.Expertise;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthDto {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String emailAddress;

    private String homeAddress;

    private Location location;

    private Expertise expertise;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String accessToken;

}
