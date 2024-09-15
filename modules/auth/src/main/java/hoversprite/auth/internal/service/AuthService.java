package hoversprite.auth.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hoversprite.auth.external.service.JwtService;
import hoversprite.auth.internal.dto.*;
import hoversprite.common.external.enums.Expertise;
import hoversprite.common.external.enums.UserRole;
import hoversprite.common.external.type.Location;
import hoversprite.common.external.util.UtilFunctions;
import hoversprite.user.external.dto.UserDto;
import hoversprite.user.external.service.UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private InMemoryClientRegistrationRepository clientRegistrationRepository;

    public AuthDto register(String fullName, String phoneNumber, String emailAddress, String homeAddress, Location location, UserRole userRole, String googleId, String facebookId, Expertise expertise, String password) throws Exception {

        String encryptedPassword = passwordEncoder.encode(password);
        UserDto newUserDto = userService.createUser(fullName, phoneNumber, emailAddress, homeAddress, location, userRole, googleId, facebookId, expertise, encryptedPassword);

        return new AuthDto(newUserDto.getId(), newUserDto.getFullName(), newUserDto.getPhoneNumber(), newUserDto.getEmailAddress(), newUserDto.getHomeAddress(), newUserDto.getLocation(), newUserDto.getUserRole(), newUserDto.getGoogleId(), newUserDto.getFacebookId(),  newUserDto.getExpertise(), newUserDto.getCreatedAt(), newUserDto.getUpdatedAt(), jwtService.generateToken(newUserDto));

    }

    public AuthDto signIn(String emailOrPhone, String password) throws Exception {

        UserDto userDto = userService.getUserByEmailAddressOrPhoneNumber(emailOrPhone);

        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new BadCredentialsException("Invalid password.");
        }

        return new AuthDto(userDto.getId(), userDto.getFullName(), userDto.getPhoneNumber(), userDto.getEmailAddress(), userDto.getHomeAddress(), userDto.getLocation(), userDto.getUserRole(), userDto.getGoogleId(), userDto.getFacebookId(), userDto.getExpertise(), userDto.getCreatedAt(), userDto.getUpdatedAt(), jwtService.generateToken(userDto));
    }

    public AuthDto getCurrentAuthenticatedUser() throws Exception {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        System.out.print(userDetails);
        UserDto userDto = userService.getUserById(Long.valueOf(userDetails.getUsername()));
        return new AuthDto(userDto.getId(), userDto.getFullName(), userDto.getPhoneNumber(), userDto.getEmailAddress(), userDto.getHomeAddress(), userDto.getLocation(), userDto.getUserRole(), userDto.getGoogleId(), userDto.getFacebookId(), userDto.getExpertise(), userDto.getCreatedAt(), userDto.getUpdatedAt(), jwtService.generateToken(userDto));
    }

    public String getOAuthGoogleUrl() {
        ClientRegistration googleRegistration = clientRegistrationRepository.findByRegistrationId("google");
        String authUri = googleRegistration.getProviderDetails().getAuthorizationUri();
        String clientId = googleRegistration.getClientId();
        String redirectUri = googleRegistration.getRedirectUri();
        String scope = URLEncoder.encode("openid profile email", StandardCharsets.UTF_8);

        return String.format("%s?response_type=code&client_id=%s&redirect_uri=%s&scope=%s",
                authUri, clientId, redirectUri, scope);
    }

    public Cookie handleGoogleCallback(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ClientRegistration googleRegistration = clientRegistrationRepository.findByRegistrationId("google");
        String tokenUri = googleRegistration.getProviderDetails().getTokenUri();
        String clientId = googleRegistration.getClientId();
        String clientSecret = googleRegistration.getClientSecret();
        String redirectUri = googleRegistration.getRedirectUri();

        String body = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code", code, clientId, clientSecret, redirectUri);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUri, HttpMethod.POST, request, String.class);

        if (tokenResponse.getStatusCode().isError()) {
            throw new IllegalArgumentException("Invalid authorization code or request.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        GoogleTokenResponseDto tokenResponseDto = objectMapper.readValue(tokenResponse.getBody(), GoogleTokenResponseDto.class);

        String userInfoUri = googleRegistration.getProviderDetails().getUserInfoEndpoint().getUri();
        headers = new HttpHeaders();
        headers.setBearerAuth(tokenResponseDto.getAccessToken());

        request = new HttpEntity<>(headers);
        ResponseEntity<String> infoResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, request, String.class);

        GoogleInfoResponseDto infoResponseDto = objectMapper.readValue(infoResponse.getBody(), GoogleInfoResponseDto.class);

        String email = infoResponseDto.getEmail();
        String fullName = infoResponseDto.getName();
        String googleId = infoResponseDto.getId();

        AuthDto authDto;
        try {
            UserDto userDto = userService.getUserByGoogleId(googleId);
            Cookie cookie = new Cookie("sessionToken", jwtService.generateToken(userDto));
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            return cookie;
        } catch (Exception idException) {
            try {
                UserDto userDto = userService.getUserByEmailAddressOrPhoneNumber(email);
                Cookie cookie = new Cookie("sessionToken", jwtService.generateToken(userDto));
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                return cookie;
            } catch (Exception emailException) {
                authDto = new AuthDto(null, fullName, null, email, null, null, null, googleId, null, null, null, null, null);
                String authJson = objectMapper.writeValueAsString(authDto);
                String encodedAuthJson = URLEncoder.encode(authJson, StandardCharsets.UTF_8);
                Cookie cookie = new Cookie("authInfo", encodedAuthJson);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                return cookie;
            }
        }
    }

    public String getOAuthFacebookUrl() {
        ClientRegistration facebookRegistration = clientRegistrationRepository.findByRegistrationId("facebook");
        String authUri = facebookRegistration.getProviderDetails().getAuthorizationUri();
        String clientId = facebookRegistration.getClientId();
        String redirectUri = facebookRegistration.getRedirectUri();
        String scope = URLEncoder.encode("email public_profile", StandardCharsets.UTF_8);

        return String.format("%s?response_type=code&client_id=%s&redirect_uri=%s&scope=%s",
                authUri, clientId, redirectUri, scope);
    }

    public Cookie handleFacebookCallback(String code) throws JsonProcessingException{
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ClientRegistration facebookRegistration = clientRegistrationRepository.findByRegistrationId("facebook");
        String tokenUri = facebookRegistration.getProviderDetails().getTokenUri();
        String clientId = facebookRegistration.getClientId();
        String clientSecret = facebookRegistration.getClientSecret();
        String redirectUri = facebookRegistration.getRedirectUri();
        String body = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code", code, clientId, clientSecret, redirectUri);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUri, HttpMethod.POST, request, String.class);

        if (tokenResponse.getStatusCode().isError()) {
            throw new IllegalArgumentException("Invalid authorization code or request.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        FacebookTokenResponseDto tokenResponseDto = objectMapper.readValue(tokenResponse.getBody(), FacebookTokenResponseDto.class);

        String userInfoUri = facebookRegistration.getProviderDetails().getUserInfoEndpoint().getUri();
        headers = new HttpHeaders();
        headers.setBearerAuth(tokenResponseDto.getAccessToken());

        request = new HttpEntity<>(headers);
        ResponseEntity<String> infoResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, request, String.class);

        FacebookInfoResponseDto infoResponseDto = objectMapper.readValue(infoResponse.getBody(), FacebookInfoResponseDto.class);

        String fullName = infoResponseDto.getFullName();
        String facebookId = infoResponseDto.getId();
        System.out.print(infoResponseDto);

        AuthDto authDto;
        try {
            UserDto userDto = userService.getUserByFacebookId(facebookId);
            Cookie cookie = new Cookie("sessionToken", jwtService.generateToken(userDto));
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            return cookie;
        } catch (Exception e) {
            authDto = new AuthDto(null, fullName, null, null, null, null, null, null, facebookId,null,null, null, null);
            String authJson = objectMapper.writeValueAsString(authDto);
            String encodedAuthJson = URLEncoder.encode(authJson, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie("authInfo", encodedAuthJson);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            return cookie;
        }

    }
}
