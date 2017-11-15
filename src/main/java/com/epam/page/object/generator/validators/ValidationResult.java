package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

public class ValidationResult {

    private boolean isValid;
    private Validator validator;
    private SearchRule searchRule;

    public ValidationResult(boolean isValid,
                            Validator validator,
                            SearchRule searchRule) {
        this.isValid = isValid;
        this.validator = validator;
        this.searchRule = searchRule;
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
        return validator.getExceptionMessage();
    }
}
