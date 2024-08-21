package internal.controller;

import shared.services.AuthService;
import internal.dtos.AuthResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import internal.dtos.RegisterRequestDTOImpl;
import internal.dtos.SignInRequestDTOImpl;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTOImpl dto) throws Exception {
        String token = authService.register(dto);
        return new ResponseEntity<>(new AuthResponseDTO("User registered successfully", token), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    ResponseEntity<AuthResponseDTO> signIn(@RequestBody SignInRequestDTOImpl dto) throws Exception {
        String token = authService.signIn(dto);
        return new ResponseEntity<>(new AuthResponseDTO("User signed in successfully", token), HttpStatus.CREATED);
    }

}
