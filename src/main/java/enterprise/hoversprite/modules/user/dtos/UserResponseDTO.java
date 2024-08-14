package enterprise.hoversprite.modules.user.dtos;

import enterprise.hoversprite.common.types.Location;
import enterprise.hoversprite.modules.user.enums.UserRole;

public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String emailAddress;
    private Location homeAddress;
    private Enum<UserRole> userRole;
    private String username;
    private boolean emailConfirmed;

    public UserResponseDTO(Long id, String fullName, String phoneNumber, String emailAddress, Location homeAddress,
            Enum<UserRole> userRole, String username, boolean emailConfirmed) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
        this.userRole = userRole;
        this.username = username;
        this.emailConfirmed = emailConfirmed;
    }

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

    public Enum<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Enum<UserRole> userRole) {
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }
}
