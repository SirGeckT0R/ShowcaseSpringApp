package com.example.showcaseapp.exception;

public class MainException extends Exception{
    public MainException() {}

    public MainException(String message) {
        super(message);
    }

    public MainException(String message, Throwable cause) {
        super(message, cause);
    }

    public MainException(Throwable cause) {
        super(cause);
    }

    public MainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
