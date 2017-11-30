package com.epam.page.object.generator.validators;

import java.util.ArrayList;
import java.util.List;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationExceptionConverter {

    private final static Logger logger = LoggerFactory.getLogger(ValidationExceptionConverter.class);

    public List<ValidationResultNew> toValidationResult(ValidationException e){

        List<ValidationResultNew> validationResults = new ArrayList<>();
        if (e.getCausingExceptions().isEmpty()) {
            validationResults.add(getValidationResult(e));
            logger.error(e.getMessage());
        } else {
            for (ValidationException validationException : e.getCausingExceptions()) {
                validationResults.add(getValidationResult(validationException));
                logger.error(validationException.getMessage());
            }
        }
        return validationResults;
    }

    private ValidationResultNew getValidationResult(ValidationException e) {
        return new ValidationResultNew(false, e.getMessage());
    }

}
