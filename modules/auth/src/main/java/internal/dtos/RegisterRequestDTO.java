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

    @Pattern(regexp = "^[A-Z][a-z]*[A-Z][a-z]+$", message = "Full name must cannot have more than 2 non-adjacent capitalized letters")
    private String fullName;

    @Pattern(regexp = "^(0|\\+84)\\s?\\d{3}\\s?\\d{3}\\s?\\d{3,4}$", message = "Phone number must starts with 0 or +84")
    private String phoneNumber;

    @Email(message = "Invalid email address format")
    @Pattern(regexp = "^[\\w.-]+@hoversprite\\.[\\w.-]+\\.(com|vn)$", message = "Email must follow the format with a domain of @hoversprite and end with .com or .vn.")
    private String emailAddress;

    private UserRole userRole;

    private Expertise expertise;

    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]+$", message = "Password must contain at least 1 number and 1 special character")
    private String password;

}
