package com.epam.page.object.generator.validator;

import com.epam.page.object.generator.model.searchrule.SearchRule;

/**
 * This class use in {@link SearchRule} implementations like list of {@link ValidationResult}.
 * Setting values occurs during validation.
 */
public class ValidationResult {

    private boolean isValid;
    private String reason;

    public ValidationResult(boolean isValid, String reason) {
        this.isValid = isValid;
        this.reason = reason;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isInvalid() {
        return !isValid;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
            "isValid=" + isValid +
            ", reason='" + reason + '\'' +
            '}';
    }
}
