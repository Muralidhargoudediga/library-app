package com.mediga.library.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException() {
    }

    public BookNotAvailableException(String message) {
        super(message);
    }

    public BookNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotAvailableException(Throwable cause) {
        super(cause);
    }

    public BookNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
