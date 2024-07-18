package enterprise.hoversprite.modules.user.model;

import enterprise.hoversprite.common.utils.Location;
import enterprise.hoversprite.modules.user.dtos.UserResponseDTO;
import jakarta.persistence.*;
import enterprise.hoversprite.modules.user.enums.UserRole;

@Entity
@Table(name = "users")
public class User {


  @Id
  @Column(name = "id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String fullName;
  private String phoneNumber;
  private String emailAddress;

  @Embedded
  private Location homeAddress;// change this to Address later

  private Enum<UserRole> userRole;
  private String username;
  private String password;

  public User() {
  }

  public User(String fullName, String phoneNumber, Location homeAddress, String emailAddress,
      Enum<UserRole> userRole,
      String username, String password) {
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
    this.homeAddress = homeAddress;
    this.emailAddress = emailAddress;
    this.userRole = userRole;
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", fullName='" + fullName + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", emailAddress='" + emailAddress + '\'' +
            ", homeAddress=" + homeAddress +
            ", userRole=" + userRole +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
  }

  public UserResponseDTO toDto() {
    return new UserResponseDTO(this.id, this.fullName, this.phoneNumber, this.emailAddress, this.homeAddress, this.userRole, this.username);
  }
}
