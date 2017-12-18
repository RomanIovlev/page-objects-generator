package com.epam.page.object.generator.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationExceptionConverter {

    private final static Logger logger = LoggerFactory
        .getLogger(ValidationExceptionConverter.class);

    public List<ValidationResult> toValidationResult(ValidationException e) {

        List<ValidationResult> validationResults = new ArrayList<>();
        if (e.getCausingExceptions().isEmpty()) {
            validationResults.add(getValidationResult(e));
            logger.error(e.getMessage(), e);
        } else {
            for (ValidationException validationException : e.getCausingExceptions()) {
                validationResults.add(getValidationResult(validationException));
                logger.error(validationException.getMessage(), e);
            }
        }
        return validationResults;
    }

    private ValidationResult getValidationResult(ValidationException e) {
        return new ValidationResult(false, e.getMessage());
    }

}
