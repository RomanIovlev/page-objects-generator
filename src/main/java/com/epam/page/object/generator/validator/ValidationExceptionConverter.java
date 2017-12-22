package com.epam.page.object.generator.validator;

import java.util.ArrayList;
import java.util.List;
import org.everit.json.schema.ValidationException;

public class ValidationExceptionConverter {

    public List<ValidationResult> toValidationResult(ValidationException e) {

        List<ValidationResult> validationResults = new ArrayList<>();
        if (e.getCausingExceptions().isEmpty()) {
            validationResults.add(getValidationResult(e));
        } else {
            for (ValidationException validationException : e.getCausingExceptions()) {
                validationResults.add(getValidationResult(validationException));
            }
        }
        return validationResults;
    }

    private ValidationResult getValidationResult(ValidationException e) {
        return new ValidationResult(false, e.getMessage());
    }

}
