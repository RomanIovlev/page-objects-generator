package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.model.SearchRule;

public class TypeSupportedValidator extends AbstractValidator {

    //TODO remove BuilderContrainer from Validator
    BuildersContainer bc = new BuildersContainer();

    public TypeSupportedValidator() {
        super(1);
    }

    public TypeSupportedValidator(int order) {
        super(order);
    }

    public TypeSupportedValidator(boolean isValidateAllSearchRules){
        super(1, isValidateAllSearchRules);
    }

    public TypeSupportedValidator(int order, boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return bc.getSupportedTypes().contains(searchRule.getType().toLowerCase());
    }

    @Override
    public String getExceptionMessage() {
        return "This type is not supported";
    }
}
