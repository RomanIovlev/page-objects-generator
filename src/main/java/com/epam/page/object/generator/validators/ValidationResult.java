package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

public class ValidationResult {

    private boolean isValid;
    private String exceptionMessage;
    private SearchRule searchRule;

    public ValidationResult(boolean isValid,
                            String exceptionMessage, SearchRule searchRule) {
        this.isValid = isValid;
        this.exceptionMessage = exceptionMessage;
        this.searchRule = searchRule;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public SearchRule getSearchRule() {
        return searchRule;
    }
}
