package com.epam.page.object.generator.error;

public class NotValidPathsException extends RuntimeException {

    public NotValidPathsException() {
    }

    public NotValidPathsException(String s) {
        super(s);
    }

    public NotValidPathsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotValidPathsException(Throwable throwable) {
        super(throwable);
    }
}