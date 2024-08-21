package shared.dtos.auth;

public interface SignInRequestDTO {
    String getEmailOrPhone();

    String getPassword();
}
