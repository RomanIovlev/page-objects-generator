package com.epam.page.object.generator.errors;

public class ValidationException extends Exception {
    public ValidationException() {
    }

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
