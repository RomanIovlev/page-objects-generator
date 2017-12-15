package com.epam.page.object.generator.error;

import java.util.List;
import java.util.stream.Collectors;

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

    public NotValidPathsException(List<String> invalidPaths) {
        super(invalidPaths.stream().collect(Collectors.joining("\n")));
    }

}
