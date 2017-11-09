package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

import java.util.Iterator;

public abstract class AbstractValidator implements Validator {

    private int priority;
    private boolean isValidateAllSearchRules = false;

    public AbstractValidator(int priority) {
        this.priority = priority;
    }

    public AbstractValidator(int priority, boolean isValidateAllSearchRules) {
        this.priority = priority;
        this.isValidateAllSearchRules = isValidateAllSearchRules;
    }

    @Override
    public void validate(ValidationContext validationContext) {

        Iterator<SearchRule> iterator;
        if (isValidateAllSearchRules) {
            iterator = validationContext.getAllSearchRules().iterator();
        } else {
            iterator = validationContext.getValidRules().iterator();
        }

        while (iterator.hasNext()) {
            SearchRule searchRule = iterator.next();
            if (!isValid(searchRule, validationContext)) {
                validationContext
                    .addValidationResult(new ValidationResult(false, this, searchRule));
            } else {
                validationContext.addValidationResult(new ValidationResult(true, this, searchRule));
            }
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setIsValidateAllSearchRules(boolean flag) {
        this.isValidateAllSearchRules = flag;
    }

    public abstract boolean isValid(SearchRule searchRule, ValidationContext validationContext);

}
