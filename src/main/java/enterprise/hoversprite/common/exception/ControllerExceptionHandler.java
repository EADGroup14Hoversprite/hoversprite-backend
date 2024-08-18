package enterprise.hoversprite.common.exception;

import enterprise.hoversprite.common.dtos.ErrorResponseDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleDefaultException(Exception e) {
        return new ResponseEntity<>(new ErrorResponseDTO("An unexpected error occurred. " + e.getClass() + ": " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponseDTO> handleSQLException(SQLException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        StringBuilder res = new StringBuilder();
        for (String err : errors) {
            res.append(err).append("\n");
        }
        return new ResponseEntity<>(new ErrorResponseDTO(res.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
