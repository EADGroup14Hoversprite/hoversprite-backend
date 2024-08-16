package enterprise.hoversprite.modules.auth.dtos;

import enterprise.hoversprite.common.enums.AuthRole;
import enterprise.hoversprite.common.enums.Expertise;
import enterprise.hoversprite.common.enums.UserRole;
import enterprise.hoversprite.common.types.Location;
import enterprise.hoversprite.modules.user.model.User;
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
    private String emailAddress;

    @NonNull
    private Location homeAddress;

    @NonNull
    private String userRole;

    private String expertise;

    @NonNull
    private String username;

    @NonNull
    private String password;

    public User toModel() {
        return switch (userRole) {
            case "FARMER" ->
                    new User(null, fullName, phoneNumber, emailAddress, homeAddress, UserRole.ROLE_FARMER, username, password, false, null, AuthRole.ROLE_USER, null, null);
            case "RECEPTIONIST" ->
                    new User(null, fullName, phoneNumber, emailAddress, homeAddress, UserRole.ROLE_RECEPTIONIST, username, password, false, null, AuthRole.ROLE_USER, null, null);
            case "SPRAYER" ->
                    new User(null, fullName, phoneNumber, emailAddress, homeAddress, UserRole.ROLE_SPRAYER, username, password, false, Expertise.APPRENTICE, AuthRole.ROLE_USER, null, null);
            default ->
                    throw new IllegalStateException("Unexpected value for user role: " + userRole + ". Should be FARMER, RECEPTIONIST or SPRAYER.");
        };
    }
}
