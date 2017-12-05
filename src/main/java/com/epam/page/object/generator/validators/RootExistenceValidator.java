package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.Set;

/**
 * {@link RootExistenceValidator} validate that complex {@link SearchRule} has 'root' inner element.
 * <br/> Default priority: 2.
 */
public class RootExistenceValidator extends AbstractValidator {

    public RootExistenceValidator() {
        super(2);
    }

    public RootExistenceValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(2, supportedSearchRuleTypes);
    }

    public RootExistenceValidator(int order) {
        super(order);
    }

    public RootExistenceValidator(boolean isValidateAllSearchRules) {
        super(2, isValidateAllSearchRules);
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
