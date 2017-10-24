package com.epam.page.object.generator.errors;

public class ValidationException extends Exception {

    public static enum ErrorCode {
        NOT_SPECIFIED,
        INCORRECT_RULES_PRESENT,
        INCORRECT_LOCATOR
    }

    private ErrorCode errorCode;

    public ValidationException() {
        errorCode = ErrorCode.NOT_SPECIFIED;
    }

    public ValidationException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ValidationException(ErrorCode errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

}