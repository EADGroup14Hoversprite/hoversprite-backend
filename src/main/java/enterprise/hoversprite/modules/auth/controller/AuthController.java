package enterprise.hoversprite.modules.auth.controller;

import enterprise.hoversprite.modules.auth.dtos.RegisterDTO;
import enterprise.hoversprite.modules.auth.dtos.SignInDTO;
import enterprise.hoversprite.modules.user.model.User;
import enterprise.hoversprite.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (!isPasswordValid(registerDTO.getPassword())) {
            return ResponseEntity.badRequest().body("Password must contain at least one capital letter and one special character.");
        }

        if (userService.findByEmail(registerDTO.getEmailAddress()) != null || userService.findByPhoneNumber(registerDTO.getPhoneNumber()) != null) {
            return ResponseEntity.badRequest().body("User already exists.");
        }

        if (!registerDTO.getEmailAddress().endsWith("@hoversprite.com")) {
            return ResponseEntity.badRequest().body("Email must belong to the domain 'hoversprite.com'.");
        }

        User user = registerDTO.toModel();
        userService.register(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInDTO signInDTO) {
        User user = userService.findByEmail(signInDTO.getUsername());
        if (user == null) {
            user = userService.findByPhoneNumber(signInDTO.getUsername());
        }
        if (user != null && new BCryptPasswordEncoder().matches(signInDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(401).body("Invalid Credentials!");
        }
    }

    @PostMapping("/email-confirmation")
    public ResponseEntity<String> emailConfirmation(@RequestParam String token) {
        //Handle email confirmation logic
        boolean isConfirmed = userService.confirmEmail(token);
        if (isConfirmed) {
            return ResponseEntity.ok("Email confirmed!");
        } else {
            return ResponseEntity.status(400).body("Invalid or expired token!");
        }
    }

    @PostMapping("/password-recovery")
    public ResponseEntity<String> recoverPassword(@RequestParam String email) {
        //Handle password recovery logic
        boolean isRecoveryEmailSent = userService.sendRecoveryEmail(email);
        if (isRecoveryEmailSent) {
            return ResponseEntity.ok("Password recovery email sent!");
        } else {
            return ResponseEntity.status(400).body("Email not Found!");
        }
    }

    private boolean isPasswordValid(String password) {
        //Password must contain at least one capital letter and one special character
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$&*]).+$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }
}
