package internal.controller;

import internal.dtos.AuthDTO;
import internal.dtos.AuthResponseDTO;
import internal.dtos.RegisterRequestDTO;
import internal.dtos.SignInRequestDTO;
import internal.service.AuthService;
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
