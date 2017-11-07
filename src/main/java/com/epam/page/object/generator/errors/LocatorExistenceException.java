package com.epam.page.object.generator.errors;

public class LocatorExistenceException extends RuntimeException {

    public LocatorExistenceException() {}

    public LocatorExistenceException(String msg) {
        super(msg);
    }

    public LocatorExistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}