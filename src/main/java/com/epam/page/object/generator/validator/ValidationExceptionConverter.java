package com.epam.page.object.generator.validator;

import com.epam.page.object.generator.model.searchrule.SearchRule;
import java.util.ArrayList;
import java.util.List;
import org.everit.json.schema.ValidationException;

/**
 * ValidationExceptionConverter converts {@link ValidationException} from JSON Schema Validator to
 * list of {@link ValidationResult} which will be set to the {@link SearchRule} as a JSON Schema
 * Validator results.
 */
public class ValidationExceptionConverter {

    /**
     * Convert {@link ValidationException} from JSON Schema Validator to list of {@link
     * ValidationResult}.
     *
     * @param exception {@link ValidationException} from JSON Schema Validator.
     * @return list of {@link ValidationResult} which will be set to SearchRule.
     */
    public List<ValidationResult> toValidationResult(ValidationException exception) {

        List<ValidationResult> validationResults = new ArrayList<>();
        if (exception.getCausingExceptions().isEmpty()) {
            validationResults.add(getValidationResult(exception));
        } else {
            for (ValidationException validationException : exception.getCausingExceptions()) {
                validationResults.add(getValidationResult(validationException));
            }
        }
        return validationResults;
    }

    private ValidationResult getValidationResult(ValidationException e) {
        return new ValidationResult(false, e.getMessage());
    }

}
