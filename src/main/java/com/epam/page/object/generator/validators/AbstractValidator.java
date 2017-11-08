package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractValidator implements Validator {

    private int order;

    public AbstractValidator(int order) {
        this.order = order;
    }

    @Override
    public void validate(ValidationContext validationContext, boolean isValidateAllSearchRules) {

        Iterator<SearchRule> iterator;
        if(isValidateAllSearchRules){
            iterator = validationContext.getAllSearchRules().iterator();
        }
        else{
            iterator = validationContext.getValidRules().iterator();
        }

        while (iterator.hasNext()) {
            SearchRule searchRule = iterator.next();
            if (!isValid(searchRule, validationContext)) {
                validationContext.addValidationResult(new ValidationResult(this, searchRule, getExceptionMessage()));
            }
            else {
                validationContext.addValidationResult(new ValidationResult(this, searchRule));
            }
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    public abstract boolean isValid(SearchRule searchRule, ValidationContext validationContext);

}
