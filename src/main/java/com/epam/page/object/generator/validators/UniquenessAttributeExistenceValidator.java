package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.io.IOException;
import java.util.Set;

/**
 * {@link UniquenessAttributeExistenceValidator} validate that {@link SearchRule} has correct unique
 * attribute. <br/> Default priority: 52.
 */
public class UniquenessAttributeExistenceValidator extends AbstractValidator {

    public UniquenessAttributeExistenceValidator() {
        super(52);
    }

    public UniquenessAttributeExistenceValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(supportedSearchRuleTypes);
    }

    public UniquenessAttributeExistenceValidator(int order) {
        super(order);
    }

    public UniquenessAttributeExistenceValidator(boolean isValidateAllSearchRules) {
        super(52, isValidateAllSearchRules);
    }

    public UniquenessAttributeExistenceValidator(int order, boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        if (searchRule.getInnerSearchRules() == null) {
            return validationContext.getUrls().stream().anyMatch(url -> {
                try {
                    return !searchRule.getRequiredValueFromFoundElement(url).isEmpty();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            });
        }

        return isInnerRulesValid(searchRule, validationContext);
    }

    @Override
    public String getExceptionMessage() {
        return "Uniqueness attribute does not exist";
    }
}
