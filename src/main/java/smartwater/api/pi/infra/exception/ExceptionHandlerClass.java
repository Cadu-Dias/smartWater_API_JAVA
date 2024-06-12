package smartwater.api.pi.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerClass {
    
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404Handler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity error400Handler(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity badCredentialsErrorHandler() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authenticationError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error while doing the Authentication");
    } 

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity accessDeniedErrorHandler() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    @ExceptionHandler(Exception.class) 
    public ResponseEntity error500Handler(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getLocalizedMessage());
    }
}
