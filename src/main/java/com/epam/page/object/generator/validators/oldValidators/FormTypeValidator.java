package com.epam.page.object.generator.validators.oldValidators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.Set;

public class FormTypeValidator extends AbstractValidator {

    private SupportedTypesContainer bc;

    public FormTypeValidator() {
        super(1);
    }

    public FormTypeValidator(Set<SearchRuleType> supportedSearchRuleTypes,
                             SupportedTypesContainer supportedTypesContainer) {
        super(1, supportedSearchRuleTypes);
        bc = supportedTypesContainer;
    }

    public FormTypeValidator(boolean isValidateAllSearchRules) {
        super(1, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return isInnerRulesValid(searchRule, validationContext) && (bc.getSupportedTypes()
            .contains(searchRule.getType()));
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {
        return "Type is not supported";
    }
}
