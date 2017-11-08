package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

public class ValidationResult {

    public ValidationResult(Validator validator,
                            SearchRule searchRule) {
        this.isValid = true;
        this.validator = validator;
        this.searchRule = searchRule;
    }

    public ValidationResult(Validator validator,
                            SearchRule searchRule,
                            String exceptionMessage) {
        this.isValid = false;
        this.validator = validator;
        this.searchRule = searchRule;
        this.exceptionMessage = exceptionMessage;
    }

    private boolean isValid;
    private Validator validator;
    private SearchRule searchRule;
    private String exceptionMessage;


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
        return exceptionMessage;
    }
}
