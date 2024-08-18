package enterprise.hoversprite.modules.auth;

import enterprise.hoversprite.modules.auth.dtos.request.RegisterRequestDTO;
import enterprise.hoversprite.modules.auth.dtos.request.SignInRequestDTO;
import enterprise.hoversprite.modules.auth.dtos.response.AuthResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto) throws Exception {
        String token = authService.register(dto);
        return new ResponseEntity<>(new AuthResponseDTO("User registered successfully", token), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> signIn(@RequestBody SignInRequestDTO dto) throws Exception {
        String token = authService.signIn(dto);
        return new ResponseEntity<>(new AuthResponseDTO("User signed in successfully", token), HttpStatus.CREATED);
    }

}
