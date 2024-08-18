package enterprise.hoversprite.modules.auth.dtos.request;

import enterprise.hoversprite.common.types.Location;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NonNull
    private String fullName;

    @NonNull
    private String phoneNumber;

    @NonNull
    @Email
    @Pattern(regexp = "^[\\w.-]+@hoversprite\\.[\\w.-]+", message = "Email must follow normal email format with email domain of @hoversprite")
    private String emailAddress;

    @NonNull
    private Location homeAddress;

    @NonNull
    private String userRole;

    private String expertise;

    @NonNull
    private String username;

    @NonNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]+$", message = "Password must contain at least 1 number and 1 special character")
    private String password;
}
