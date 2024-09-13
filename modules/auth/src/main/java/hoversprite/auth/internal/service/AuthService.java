package hoversprite.auth.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hoversprite.auth.external.service.JwtService;
import hoversprite.auth.internal.dto.AuthDto;
import hoversprite.auth.internal.dto.GoogleInfoResponseDto;
import hoversprite.auth.internal.dto.GoogleTokenResponseDto;
import hoversprite.common.external.enums.Expertise;
import hoversprite.common.external.enums.UserRole;
import hoversprite.common.external.type.Location;
import hoversprite.user.external.dto.UserDto;
import hoversprite.user.external.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthDto register(String fullName, String phoneNumber, String emailAddress, String homeAddress, Location location, UserRole userRole, Expertise expertise, String username, String password) throws Exception {

        String encryptedPassword = passwordEncoder.encode(password);
        UserDto newUserDto = userService.createUser(fullName, phoneNumber, emailAddress, homeAddress, location, userRole, expertise, username, encryptedPassword);

        return new AuthDto(newUserDto.getId(), newUserDto.getFullName(), newUserDto.getPhoneNumber(), newUserDto.getEmailAddress(), newUserDto.getExpertise(), newUserDto.getCreatedAt(), newUserDto.getUpdatedAt(), jwtService.generateToken(newUserDto));
    }

    public AuthDto signIn(String emailOrPhone, String password) throws Exception {

        UserDto userDto = userService.getUserByEmailAddressOrPhoneNumber(emailOrPhone);

        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return new AuthDto(userDto.getId(), userDto.getFullName(), userDto.getPhoneNumber(), userDto.getEmailAddress(), userDto.getExpertise(), userDto.getCreatedAt(), userDto.getUpdatedAt(), jwtService.generateToken(userDto));
    }

//    public String getOAuthGoogleUrl() {
//        ClientRegistration googleRegistration = clientRegistrationRepository.findByRegistrationId("google");
//        String authUri = googleRegistration.getProviderDetails().getAuthorizationUri();
//        String clientId = googleRegistration.getClientId();
//        String redirectUri = googleRegistration.getRedirectUri();
//        String scope = URLEncoder.encode("openid profile email", StandardCharsets.UTF_8);
//
//        return String.format("%s?response_type=code&client_id=%s&redirect_uri=%s&scope=%s",
//                authUri, clientId, redirectUri, scope);
//    }

    public AuthDto handleGoogleCallback(String email) throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//        String tokenUri = "https://oauth2.googleapis.com/token";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        ClientRegistration googleRegistration = clientRegistrationRepository.findByRegistrationId("google");
//        String clientId = googleRegistration.getClientId();
//        String clientSecret = googleRegistration.getClientSecret();
//        String redirectUri = googleRegistration.getRedirectUri();
//
//        String body = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code", code, clientId, clientSecret, redirectUri);
//
//        HttpEntity<String> request = new HttpEntity<>(body, headers);
//        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUri, HttpMethod.POST, request, String.class);
//
//        if (tokenResponse.getStatusCode().isError()) {
//            throw new IllegalArgumentException("Invalid authorization code or request.");
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        GoogleTokenResponseDto tokenResponseDto = objectMapper.readValue(tokenResponse.getBody(), GoogleTokenResponseDto.class);
//
//        String userInfoUri = "https://www.googleapis.com/oauth2/v3/userinfo";
//        headers = new HttpHeaders();
//        headers.setBearerAuth(tokenResponseDto.getAccessToken());
//
//        request = new HttpEntity<>(headers);
//        ResponseEntity<String> infoResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, request, String.class);
//
//        GoogleInfoResponseDto infoResponseDto = objectMapper.readValue(infoResponse.getBody(), GoogleInfoResponseDto.class);
//
//        String email = infoResponseDto.getEmail();
//        String fullName = infoResponseDto.getName();

        try {
            UserDto userDto = userService.getUserByEmailAddressOrPhoneNumber(email);
            return new AuthDto(userDto.getId(), userDto.getFullName(), userDto.getPhoneNumber(), userDto.getEmailAddress(), userDto.getExpertise(), userDto.getCreatedAt(), userDto.getUpdatedAt(), jwtService.generateToken(userDto));
        } catch (Exception e) {
            return null;
        }
    }
}
