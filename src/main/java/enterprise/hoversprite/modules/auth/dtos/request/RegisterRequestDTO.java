package enterprise.hoversprite.modules.auth.dtos.request;

import enterprise.hoversprite.common.types.Location;
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
}
