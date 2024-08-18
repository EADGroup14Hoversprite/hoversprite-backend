package dtos.request;

import enums.Expertise;
import enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import types.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NonNull
    private String fullName;

    @NonNull
    private String phoneNumber;

    @NonNull
    @Email(message = "Invalid email address format")
    @Pattern(regexp = "^[\\w.-]+@hoversprite\\.[\\w.-]+", message = "Email must follow normal email format with email domain of @hoversprite")
    private String emailAddress;

    @NonNull
    private Location homeAddress;

    @NonNull
    private UserRole userRole;

    private Expertise expertise;

    @NonNull
    private String username;

    @NonNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]+$", message = "Password must contain at least 1 number and 1 special character")
    private String password;
}
