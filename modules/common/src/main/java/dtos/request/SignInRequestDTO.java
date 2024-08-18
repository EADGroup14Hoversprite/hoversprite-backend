package dtos.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class SignInRequestDTO {

    @NonNull
    private String emailOrPhone;

    @NonNull
    private String password;

}
