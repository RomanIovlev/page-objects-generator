package com.epam.page.object.generator.error;

public class NotValidUrlException extends RuntimeException {

    public NotValidUrlException() {
    }

    public NotValidUrlException(String s) {
        super(s);
    }

    public NotValidUrlException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotValidUrlException(Throwable throwable) {
        super(throwable);
    }
}
