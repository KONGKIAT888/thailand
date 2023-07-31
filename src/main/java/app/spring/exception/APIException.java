package app.spring.exception;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public APIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public APIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

}