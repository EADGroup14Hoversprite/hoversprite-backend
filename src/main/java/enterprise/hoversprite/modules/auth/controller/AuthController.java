package enterprise.hoversprite.modules.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public String register() {
        return "Auth register";
    }

    @PostMapping("/sign-in")
    public String signIn() {
        return "Auth sign in";
    }

    @PostMapping("/email-confirmation")
    public String emailConfirmation() {
        return "Auth email confirmation";
    }

    @PostMapping("/password-recovery")
    public String recoverPassword() {
       return "Auth password recovery";
    }
}
