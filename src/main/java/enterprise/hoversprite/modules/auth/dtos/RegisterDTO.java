package enterprise.hoversprite.modules.auth.dtos;

import enterprise.hoversprite.common.utils.Location;
import enterprise.hoversprite.modules.user.enums.UserRole;
import enterprise.hoversprite.modules.user.model.User;

public class RegisterDTO {
    private String fullName;
    private String phoneNumber;
    private String emailAddress;
    private Location homeAddress;
    private String userRole;
    private String username;
    private String password;

    public User toModel() {
        return switch (userRole) {
            case "FARMER" ->
                    new User(fullName, phoneNumber, homeAddress, emailAddress, UserRole.FARMER, username, password, false);
            case "RECEPTIONIST" ->
                    new User(fullName, phoneNumber, homeAddress, emailAddress, UserRole.RECEPTIONIST, username, password, false);
            case "SPRAYER" ->
                    new User(fullName, phoneNumber, homeAddress, emailAddress, UserRole.SPRAYER, username, password, false);
            default -> throw new IllegalStateException("Unexpected value: " + userRole);
        };
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
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
}
