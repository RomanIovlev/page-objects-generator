package com.epam.page.object.generator.errors;

import com.epam.page.object.generator.validators.oldValidators.ValidationContext;

public class ValidationException extends RuntimeException {

    public ValidationException() {
    }

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ValidationException(ValidationContext validationContext) {
        super(validationContext.getExceptionsAboutInvalidRules());
    }

}