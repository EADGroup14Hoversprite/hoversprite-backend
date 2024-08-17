package enterprise.hoversprite.modules.auth.controller;

import enterprise.hoversprite.modules.auth.dtos.AuthResponseDTO;
import enterprise.hoversprite.modules.auth.dtos.RegisterRequestDTO;
import enterprise.hoversprite.modules.auth.dtos.SignInRequestDTO;
import enterprise.hoversprite.modules.auth.service.IAuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerDTO) throws Exception {
        String token = authService.register(registerDTO.toModel());
        return new ResponseEntity<>(new AuthResponseDTO("User registered successfully", token), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> signIn(@RequestBody SignInRequestDTO signInDTO) throws Exception {
        String token = authService.signIn(signInDTO);
        return new ResponseEntity<>(new AuthResponseDTO("User signed in successfully", token), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Secured("USER")
    @PostMapping("/email-confirmation")
    public ResponseEntity<String> emailConfirmation(@RequestParam String token) {
        return null;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Secured("USER")
    @PostMapping("/password-recovery")
    public ResponseEntity<String> recoverPassword(@RequestParam String email) {
        return null;
    }
}
