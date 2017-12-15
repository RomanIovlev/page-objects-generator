package com.epam.page.object.generator.error;

public class ValidationException extends RuntimeException {

    public ValidationException() {
    }

    public ValidationException(String msg) {
        super("\n" + msg);
    }

    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}