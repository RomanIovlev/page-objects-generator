package com.epam.page.object.generator.errors;

public class TypeSupportedException extends RuntimeException {

    public TypeSupportedException() {}

    public TypeSupportedException(String msg) {
        super(msg);
    }

    public TypeSupportedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}