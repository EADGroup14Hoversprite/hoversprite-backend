package internal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import internal.dtos.UserAuthInfoDTO;
import internal.dtos.UserInfoDTO;
import shared.enums.AuthRole;
import shared.enums.Expertise;
import shared.enums.UserRole;
import shared.types.Location;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

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
