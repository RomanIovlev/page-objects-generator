package com.epam.page.object.generator.validators.oldValidators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.Set;

/**
 * {@link TypeSupportedValidator} visit that {@link SearchRule} has correct "type" attribute
 * which supported by the program. <br/> Default priority: 1.
 */
public class TypeSupportedValidator extends AbstractValidator {

    private SupportedTypesContainer bc;

    public TypeSupportedValidator() {
        super(1);
    }

    public TypeSupportedValidator(Set<SearchRuleType> supportedSearchRuleTypes,
                                  SupportedTypesContainer supportedTypesContainer) {
        super(1, supportedSearchRuleTypes);
        bc = supportedTypesContainer;
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
