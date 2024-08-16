package enterprise.hoversprite.common.jwt;

import enterprise.hoversprite.common.dtos.AuthErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<AuthErrorResponseDTO> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(new AuthErrorResponseDTO("Unauthorized"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<AuthErrorResponseDTO> handleAccessDeniedException(AuthenticationException e) {
        return new ResponseEntity<>(new AuthErrorResponseDTO("Forbidden"), HttpStatus.FORBIDDEN);
    }
}
