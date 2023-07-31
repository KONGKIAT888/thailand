package app.spring.exception;

import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorExceptions {

    private final int status;
    private final String error;
    private final String path;
    private final Date timestamp;
    private final String message;
    private final String details;

    public ErrorExceptions(int status, String error, String path, Date timestamp, String message, String details) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
