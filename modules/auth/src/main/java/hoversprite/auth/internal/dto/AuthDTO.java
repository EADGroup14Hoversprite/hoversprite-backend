package hoversprite.auth.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.common.external.enums.Expertise;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthDTO {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String emailAddress;

    private Expertise expertise;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String accessToken;

}
