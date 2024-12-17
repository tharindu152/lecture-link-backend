package lk.ac.iit.lecture_link.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    private final int errorCode;

    public AppException(int errorCode) {
        this.errorCode = errorCode;
    }

    public AppException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public AppException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
