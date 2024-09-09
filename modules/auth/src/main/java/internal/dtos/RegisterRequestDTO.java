package internal.dtos;

import lombok.*;
import shared.enums.Expertise;
import shared.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import shared.types.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    private String fullName;

    private String phoneNumber;

    @Email(message = "Invalid email address format")
    @Pattern(regexp = "^[\\w.-]+@hoversprite\\.[\\w.-]+", message = "Email must follow normal email format with email domain of @hoversprite")
    private String emailAddress;

    private Location homeAddress;

    private UserRole userRole;

    private Expertise expertise;

    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]+$", message = "Password must contain at least 1 number and 1 special character")
    private String password;

}
