package shared.dtos;

import lombok.*;
import shared.enums.Expertise;
import shared.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import shared.types.Location;

public class RegisterRequestDTO {

    private String fullName;

    private String phoneNumber;

    @Email(message = "Invalid email address format")
    @Pattern(regexp = "^[\\w.-]+@hoversprite\\.[\\w.-]+", message = "Email must follow normal email format with email domain of @hoversprite")
    private String emailAddress;

    private Location homeAddress;

    private UserRole userRole;

    private Expertise expertise;

    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]+$", message = "Password must contain at least 1 number and 1 special character")
    private String password;

    public RegisterRequestDTO(String fullName, String phoneNumber, String emailAddress, Location homeAddress, UserRole userRole, Expertise expertise, String username, String password) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
        this.userRole = userRole;
        this.expertise = expertise;
        this.username = username;
        this.password = password;
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

    public @Email(message = "Invalid email address format") @Pattern(regexp = "^[\\w.-]+@hoversprite\\.[\\w.-]+", message = "Email must follow normal email format with email domain of @hoversprite") String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(@Email(message = "Invalid email address format") @Pattern(regexp = "^[\\w.-]+@hoversprite\\.[\\w.-]+", message = "Email must follow normal email format with email domain of @hoversprite") String emailAddress) {
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

    public Expertise getExpertise() {
        return expertise;
    }

    public void setExpertise(Expertise expertise) {
        this.expertise = expertise;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]+$", message = "Password must contain at least 1 number and 1 special character") String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]+$", message = "Password must contain at least 1 number and 1 special character") String password) {
        this.password = password;
    }
}
