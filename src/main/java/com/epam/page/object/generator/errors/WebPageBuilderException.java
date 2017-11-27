package com.epam.page.object.generator.errors;

public class WebPageBuilderException extends Exception {


    public WebPageBuilderException(String message) {
        super(message);
    }

    public WebPageBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebPageBuilderException(Throwable cause) {
        super(cause);
    }
}
