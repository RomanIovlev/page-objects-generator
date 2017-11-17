package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

public class ValidationResult {

    private boolean isValid;
    private AbstractValidator validator;
    private SearchRule searchRule;
    private ValidationContext validationContext;

    public ValidationResult(boolean isValid,
                            AbstractValidator validator,
                            SearchRule searchRule, ValidationContext validationContext) {
        this.isValid = isValid;
        this.validator = validator;
        this.searchRule = searchRule;
        this.validationContext = validationContext;
    }

    public boolean isValid() {
        return isValid;
    }

    public Validator getValidator() {
        return validator;
    }

    public SearchRule getSearchRule() {
        return searchRule;
    }

    public String getExceptionMessage() {
        return validator.getExceptionMessage(searchRule, validationContext);
    }
}
