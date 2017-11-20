package com.epam.page.object.generator.validators;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.epam.page.object.generator.model.SearchRule;

/**
 * {@link LocatorExistenceValidator} validate that {@link SearchRule} has any locator (xpath or
 * css).<br/> Default priority: 0.
 */
public class LocatorExistenceValidator extends AbstractValidator {

    public LocatorExistenceValidator() {
        super(0);
    }

    public LocatorExistenceValidator(int order) {
        super(order);
    }

    public LocatorExistenceValidator(boolean isValidateAllSearchRules) {
        super(0, isValidateAllSearchRules);
    }

    public LocatorExistenceValidator(int order, boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        boolean isValid = isInnerRulesValid(searchRule, validationContext);
        if( searchRule.getInnerSearchRules() != null){
            return isValid;
        }
        return isValid && (!isEmpty(searchRule.getCss()) || !isEmpty(searchRule.getXpath()));
    }

    @Override
    public String getExceptionMessage() {
        return "No xpath or css locator";
    }
}
