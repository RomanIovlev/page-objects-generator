package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.ValidationException;

public class IntermediateCheckValidator implements Validator {

    private boolean isValidateAllSearchRules = false;

    public IntermediateCheckValidator() {
    }

    @Override
    public void validate(ValidationContext validationContext) {
        if (validationContext.hasInvalidRules()) {
            StringBuilder stringBuilder = new StringBuilder("\n");
            for (ValidationResult validationResult : validationContext.getValidationResults()) {
                if (!validationResult.isValid()) {
                    stringBuilder
                        .append(validationResult.getExceptionMessage())
                        .append(": ")
                        .append(validationResult.getSearchRule())
                        .append("\n");
                }
            }

            throw new ValidationException(stringBuilder.toString());
        }
    }

    @Override
    public int getPriority() {
        return 50;
    }

    @Override
    public String getExceptionMessage() {
        return null;
    }

    @Override
    public void setIsValidateAllSearchRules(boolean flag) {
        this.isValidateAllSearchRules = isValidateAllSearchRules;
    }
}
