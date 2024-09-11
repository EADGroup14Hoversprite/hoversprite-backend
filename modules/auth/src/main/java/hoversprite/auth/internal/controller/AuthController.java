package hoversprite.auth.internal.controller;

import hoversprite.auth.internal.dto.AuthDTO;
import hoversprite.auth.internal.dto.AuthResponseDTO;
import hoversprite.auth.internal.dto.RegisterRequestDTO;
import hoversprite.auth.internal.dto.SignInRequestDTO;
import hoversprite.auth.internal.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto) throws Exception {
        AuthDTO authDto = authService.register(dto);
        return new ResponseEntity<>(new AuthResponseDTO("User registered successfully", authDto), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    ResponseEntity<AuthResponseDTO> signIn(@RequestBody SignInRequestDTO dto) throws Exception {
        AuthDTO authDTO = authService.signIn(dto);
        return new ResponseEntity<>(new AuthResponseDTO("User signed in successfully", authDTO), HttpStatus.CREATED);
    }

}
