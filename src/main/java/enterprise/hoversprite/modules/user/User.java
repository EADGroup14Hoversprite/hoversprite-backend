package enterprise.hoversprite.modules.user;

import enterprise.hoversprite.common.enums.AuthRole;
import enterprise.hoversprite.common.enums.Expertise;
import enterprise.hoversprite.common.enums.UserRole;
import enterprise.hoversprite.common.types.Location;
import enterprise.hoversprite.modules.user.dtos.UserAuthInfoDTO;
import enterprise.hoversprite.modules.user.dtos.UserInfoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Embedded
    private Location homeAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean emailConfirmed;

    @Enumerated(EnumType.STRING)
    private Expertise expertise;

    @Column(nullable = false)
    private AuthRole authRole;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public UserAuthInfoDTO toUserAuthInfoDto() {
        return new UserAuthInfoDTO(id, password, userRole, authRole);
    }

    public UserInfoDTO toUserInfoDto() {
        return new UserInfoDTO(id, fullName, phoneNumber, emailAddress, homeAddress, emailConfirmed, expertise, createdAt, updatedAt);
    }
}
