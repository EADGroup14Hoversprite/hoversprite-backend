package enterprise.hoversprite.modules.auth.controller;

import enterprise.hoversprite.modules.auth.dtos.RegisterDTO;
import enterprise.hoversprite.modules.auth.dtos.SignInDTO;
import enterprise.hoversprite.modules.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        // Return the jwt string
        return null;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInDTO signInDTO) {
        return null;
    }

    @PostMapping("/email-confirmation")
    public ResponseEntity<String> emailConfirmation(@RequestParam String token) {
        return null;
    }

    @PostMapping("/password-recovery")
    public ResponseEntity<String> recoverPassword(@RequestParam String email) {
        return null;
    }
}
