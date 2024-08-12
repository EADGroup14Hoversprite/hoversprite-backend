package enterprise.hoversprite.modules.user.model;

import enterprise.hoversprite.common.utils.Location;
import enterprise.hoversprite.modules.user.dtos.UserResponseDTO;
import enterprise.hoversprite.modules.user.enums.UserRole;
import jakarta.persistence.*;

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
  private Location homeAddress; // change this to Address later

  @Enumerated(EnumType.STRING)
  private UserRole userRole;

  private String username;
  private String password;
  private boolean emailConfirmed;

  private String expertise;

  public User() {
    // Default constructor for JPA
  }

  public User(String fullName, String phoneNumber, Location homeAddress, String emailAddress,
              UserRole userRole, String username, String password, boolean emailConfirmed) {
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
    this.homeAddress = homeAddress;
    this.emailAddress = emailAddress;
    this.userRole = userRole;
    this.username = username;
    this.password = password;
    this.emailConfirmed = emailConfirmed;
  }

  public User(String fullName, String phoneNumber, Location homeAddress, String emailAddress,
              UserRole userRole, String username, String password, boolean emailConfirmed, String expertise) {
    this(fullName, phoneNumber, homeAddress, emailAddress, userRole, username, password, emailConfirmed);
    this.expertise = expertise;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public Location getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Location homeAddress) {
    this.homeAddress = homeAddress;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEmailConfirmed() {
    return emailConfirmed;
  }

  public void setEmailConfirmed(boolean emailConfirmed) {
    this.emailConfirmed = emailConfirmed;
  }

  public String getExpertise() {
    return expertise;
  }

  public void setExpertise(String expertise) {
    this.expertise = expertise;
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
            ", emailConfirmed=" + emailConfirmed +
            ", expertise='" + expertise + '\'' +
            '}';
  }

  public UserResponseDTO toDto() {
    return new UserResponseDTO(this.id, this.fullName, this.phoneNumber, this.emailAddress, this.homeAddress, this.userRole, this.username, this.emailConfirmed);
  }
}
