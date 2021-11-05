package com.mediga.library.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException() {
    }

    public LoanNotFoundException(String message) {
        super(message);
    }

    public LoanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanNotFoundException(Throwable cause) {
        super(cause);
    }

    public LoanNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
