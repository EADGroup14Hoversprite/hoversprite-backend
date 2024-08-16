package enterprise.hoversprite.modules.user.dtos;

import enterprise.hoversprite.common.enums.Expertise;
import enterprise.hoversprite.common.enums.UserRole;
import enterprise.hoversprite.common.types.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String emailAddress;

    private Location homeAddress;

    private UserRole userRole;

    private String username;

    private Expertise expertise;

    private Date createdAt;

    private Date updatedAt;

}
