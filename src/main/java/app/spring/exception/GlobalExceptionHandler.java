package app.spring.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorExceptions> handlerResourceNotFoundException(ResourceNotFoundException e, WebRequest w) {
        String url = ((ServletWebRequest) w).getRequest().getRequestURI();
        ErrorExceptions errorExceptions = new ErrorExceptions(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), url, new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<>(errorExceptions, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorExceptions> handlerAPIException(APIException e, WebRequest w) {
        String url = ((ServletWebRequest) w).getRequest().getRequestURI();
        ErrorExceptions errorExceptions = new ErrorExceptions(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), url, new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<>(errorExceptions, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorExceptions> handlerGlobalException(Exception e, WebRequest w) {
        String url = ((ServletWebRequest) w).getRequest().getRequestURI();
        ErrorExceptions errorExceptions = new ErrorExceptions(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), url, new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<>(errorExceptions, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatusCode status,
                                                                  @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorExceptions> handleAccessDeniedException(AccessDeniedException e, WebRequest w) {
        String url = ((ServletWebRequest) w).getRequest().getRequestURI();
        ErrorExceptions errorExceptions = new ErrorExceptions(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), url, new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<>(errorExceptions, HttpStatus.UNAUTHORIZED);
    }

}
