package com.epam.page.object.generator.errors;

import java.util.List;
import java.util.stream.Collectors;

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

    public NotValidUrlException(List<String> invalidUrls) {
        super(invalidUrls.stream().collect(Collectors.joining("")));
    }
}
