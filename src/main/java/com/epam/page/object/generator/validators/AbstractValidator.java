package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

import java.util.Iterator;

public abstract class AbstractValidator implements Validator {

    private int order;
    private RuntimeException ex;

    public AbstractValidator(int order, RuntimeException ex) {
        this.order = order;
        this.ex = ex;
    }

    @Override
    public RuntimeException getException() {
        return ex;
    }


    @Override
    public void validate(ValidationContext context){
        Iterator<SearchRule> iterator = context.getValidRules().iterator();

        while (iterator.hasNext()) {
            SearchRule searchRule = iterator.next();
            if (!validate(searchRule)) {
                context.addRuleToInvalid(searchRule, getException());
                iterator.remove();
            }
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    public abstract boolean validate(SearchRule searchRule);


}
