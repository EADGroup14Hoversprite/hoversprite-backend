package hoversprite.auth.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hoversprite.auth.internal.dto.*;
import hoversprite.auth.internal.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) throws Exception {
        AuthDto authDto = authService.register(dto.getFullName(), dto.getPhoneNumber(), dto.getEmailAddress(), dto.getHomeAddress(), dto.getLocation(), dto.getUserRole(), dto.getExpertise(), dto.getUsername(), dto.getPassword());
        return new ResponseEntity<>(new AuthResponseDto("User registered successfully", authDto), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    ResponseEntity<AuthResponseDto> signIn(@RequestBody SignInRequestDto dto) throws Exception {
        AuthDto authDTO = authService.signIn(dto.getEmailOrPhone(), dto.getPassword());
        return new ResponseEntity<>(new AuthResponseDto("User signed in successfully", authDTO), HttpStatus.CREATED);
    }

//    @GetMapping("/google/redirect")
//    ResponseEntity<OAuthUrlResponseDto> getGoogleOAuthUrl() {
//        return new ResponseEntity<>(new OAuthUrlResponseDto(authService.getOAuthGoogleUrl()), HttpStatus.OK);
//    }

    @GetMapping("/google/callback")
    ResponseEntity<AuthResponseDto> handleGoogleCallback(@RequestParam String email) throws JsonProcessingException {
        AuthDto authDto = authService.handleGoogleCallback(email);
        return new ResponseEntity<>(new AuthResponseDto(authDto == null ? "User is not registered" : "User signed in with Google successfully.", authDto), HttpStatus.OK);
    }

}
