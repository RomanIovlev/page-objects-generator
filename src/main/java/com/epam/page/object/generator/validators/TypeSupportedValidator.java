package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;

/**
 * {@link TypeSupportedValidator} validate that {@link SearchRule} has correct "type" attribute
 * which supported by the program. <br/> Default priority: 1.
 */
public class TypeSupportedValidator extends AbstractValidator {

    //TODO remove BuilderContrainer from Validator
    private SupportedTypesContainer bc = new SupportedTypesContainer();

    public TypeSupportedValidator() {
        super(1);
    }

    public TypeSupportedValidator(int order) {
        super(order);
    }

    public TypeSupportedValidator(boolean isValidateAllSearchRules) {
        super(1, isValidateAllSearchRules);
    }

    public TypeSupportedValidator(int order, boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return bc.getSupportedTypes().contains(searchRule.getType());
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {
        return "This type: " + searchRule.getType() + " is not supported";
    }
}
