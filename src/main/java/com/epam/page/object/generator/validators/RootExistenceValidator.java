package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

public class RootExistenceValidator extends AbstractValidator {

    public RootExistenceValidator() {
        super(0);
    }

    public RootExistenceValidator(int order) {
        super(order);
    }

    public RootExistenceValidator(boolean isValidateAllSearchRules) {
        super(0, isValidateAllSearchRules);
    }

    public RootExistenceValidator(int order, boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return
            searchRule.getInnerSearchRules().stream().filter(o -> o.getUniqueness() != null).count()
                == 1;
    }

    @Override
    public String getExceptionMessage() {
        return "Inner search rule with uniqueness field is missing";
    }
}
