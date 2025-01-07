package org.example.springauthbatch.Exception;

public class UtilProcessException extends RuntimeException {
    private final int   errorCode;

    public UtilProcessException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UtilProcessException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int  getErrorCode() {
        return (errorCode);
    }

}
