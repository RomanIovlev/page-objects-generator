package com.epam.page.object.generator.validators.oldValidators;

import com.epam.page.object.generator.model.SearchRule;

/**
 * {@link RootExistenceValidator} visit that complex {@link SearchRule} has 'root' inner element.
 * <br/> Default priority: 3.
 */
public class RootExistenceValidator extends AbstractValidator {

    public RootExistenceValidator() {
        super(3);
    }

    public RootExistenceValidator(int order) {
        super(order);
    }

    public RootExistenceValidator(boolean isValidateAllSearchRules) {
        super(3, isValidateAllSearchRules);
    }

    public RootExistenceValidator(int order, boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return searchRule.getRootInnerRule().isPresent();
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {
        return "Search rule hasn't got 'root' inner element";
    }
}
