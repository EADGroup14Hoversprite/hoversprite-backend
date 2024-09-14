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

    @Pattern(regexp = "^(?=\\b[A-Za-z]*[A-Z][a-z]*[A-Z]?[a-z]*\\b)[A-Za-z ]+$", message = "Full name cannot have more than 2 capitalized letters in a word and must contain only letters and spaces")
    private String fullName;

    @Pattern(regexp = "^((\\+84\\s\\d{3}\\s\\d{3}\\s\\d{3})|(0\\d{3}\\s\\d{3}\\s\\d{3})|((\\+84|0)\\d{9}))$", message = "Phone number must start with +84 or 0. If it starts with +84, it should be formatted as +84 888 888 888. If it starts with 0, it should be formatted as 0888 888 888. Otherwise, no spaces should be included.")
    private String phoneNumber;

    @Email(message = "Invalid email address format")
    @Pattern(regexp = "^[\\w.-]+@(hoversprite\\.(com|vn)|gmail\\.com)$", message = "Email must follow the format with a domain of @hoversprite.com, @hoversprite.vn, or @gmail.com.")
    private String emailAddress;

    private String homeAddress;

    private Location location;

    private UserRole userRole;

    private Expertise expertise;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]{6,}$", message = "Password must be at least 6 characters long, contain at least 1 number, and 1 special character")
    private String password;

}
