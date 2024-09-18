package hoversprite.auth.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hoversprite.auth.internal.dto.*;
import hoversprite.auth.internal.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) throws Exception {
        AuthDto authDto = authService.register(dto.getFullName(), dto.getPhoneNumber(), dto.getEmailAddress(), dto.getHomeAddress(), dto.getLocation(), dto.getUserRole(), dto.getGoogleId(), dto.getFacebookId(), dto.getExpertise(), dto.getPassword());
        return new ResponseEntity<>(new AuthResponseDto("User registered successfully", authDto), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    ResponseEntity<AuthResponseDto> signIn(@RequestBody SignInRequestDto dto) throws Exception {
        AuthDto authDto = authService.signIn(dto.getEmailOrPhone(), dto.getPassword());
        return new ResponseEntity<>(new AuthResponseDto("User signed in successfully", authDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    ResponseEntity<AuthResponseDto> getCurrentAuthenticatedUser() throws Exception {
        AuthDto authDto = authService.getCurrentAuthenticatedUser();
        return new ResponseEntity<>(new AuthResponseDto("Current authenticated user info retrieved", authDto), HttpStatus.CREATED);
    }

    @GetMapping("/google/redirect")
    ResponseEntity<OAuthUrlResponseDto> getGoogleOAuthUrl() {
        return new ResponseEntity<>(new OAuthUrlResponseDto(authService.getOAuthGoogleUrl()), HttpStatus.OK);
    }

    @GetMapping("/google/callback")
    ResponseEntity<Void> handleGoogleCallback(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        Cookie authCookie = authService.handleGoogleCallback(code);
        return handleOAuthCallbackRedirect(response, authCookie);
    }

    @GetMapping("/facebook/redirect")
    ResponseEntity<OAuthUrlResponseDto> getFacebookOAuthUrl() {
        return new ResponseEntity<>(new OAuthUrlResponseDto(authService.getOAuthFacebookUrl()), HttpStatus.OK);
    }

    @GetMapping("/facebook/callback")
    ResponseEntity<Void> handleFacebookCallback(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        Cookie authCookie = authService.handleFacebookCallback(code);
        return handleOAuthCallbackRedirect(response, authCookie);
    }

    private ResponseEntity<Void> handleOAuthCallbackRedirect(HttpServletResponse response, Cookie authCookie) {
        response.addCookie(authCookie);

        if (authCookie.getName().equals("authInfo")) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("https://localhost:3000/auth/redirect-register"))
                    .build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("https://localhost:3000/farmer/orders"))
                .build();
    }
}
