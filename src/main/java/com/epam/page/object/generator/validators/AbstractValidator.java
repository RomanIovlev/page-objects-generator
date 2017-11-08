package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

import java.util.Iterator;

public abstract class AbstractValidator implements Validator {

    private int order;
    private RuntimeException ex;
    private ValidationContext validationContext;

    public AbstractValidator(int order, RuntimeException ex) {
        this.order = order;
        this.ex = ex;
    }

    @Override
    public ValidationContext getValidationContext() {
        return validationContext;
    }

    @Override
    public void setValidationContext(ValidationContext validationContext) {
        this.validationContext = validationContext;
    }

    @Override
    public RuntimeException getException() {
        return ex;
    }


    @Override
    public void validate() {
        Iterator<SearchRule> iterator = getValidationContext().getValidRules().iterator();

        while (iterator.hasNext()) {
            SearchRule searchRule = iterator.next();
            if (!isValid(searchRule)) {
                getValidationContext().addRuleToInvalid(searchRule, getException());
                iterator.remove();
            }
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    public abstract boolean isValid(SearchRule searchRule);

}
