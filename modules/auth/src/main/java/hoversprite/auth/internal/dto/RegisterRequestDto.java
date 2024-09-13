package hoversprite.auth.internal.dto;

import hoversprite.common.external.type.Location;
import lombok.*;
import hoversprite.common.external.enums.Expertise;
import hoversprite.common.external.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @Pattern(regexp = "^[A-Z][a-z]*[A-Z][a-z]+$", message = "Full name must cannot have more than 2 non-adjacent capitalized letters")
    private String fullName;

    @Pattern(regexp = "^(0|\\+84)\\s?\\d{3}\\s?\\d{3}\\s?\\d{3,4}$", message = "Phone number must start with 0 or +84, followed by nine or ten digits, and can include spaces. For example: 0862 123 456, +84 862 123 456, or 0862123456.")
    private String phoneNumber;

    @Email(message = "Invalid email address format")
    @Pattern(regexp = "^[\\w.-]+@hoversprite\\.(com|vn)$", message = "Email must follow the format with a domain of @hoversprite.com or @hoversprite.vn.")
    private String emailAddress;

    private String homeAddress;

    private Location location;

    private UserRole userRole;

    private Expertise expertise;

    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]{6,}$", message = "Password must be at least 6 characters long, contain at least 1 number, and 1 special character")
    private String password;

}
