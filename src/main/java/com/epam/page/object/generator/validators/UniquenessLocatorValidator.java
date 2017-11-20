package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.io.IOException;
import java.util.Set;

/**
 * {@link UniquenessLocatorValidator} validate that {@link SearchRule} has uniqueness locator. <br/>
 * Default priority: 51.
 */
public class UniquenessLocatorValidator extends AbstractValidator {

    public UniquenessLocatorValidator() {
        super(52);
    }

    public UniquenessLocatorValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(52, supportedSearchRuleTypes);
    }

    public UniquenessLocatorValidator(int order) {
        super(order);
    }

    public UniquenessLocatorValidator(boolean isValidateAllSearchRules) {
        super(52, isValidateAllSearchRules);
    }

    public UniquenessLocatorValidator(int order, boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return isInnerRulesValid(searchRule, validationContext) && validationContext.getUrls()
            .stream()
            .allMatch(url -> {
                try {
                    return searchRule.extractElementsFromWebSite(url).size() <= 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            });
    }

    @Override
    public String getExceptionMessage() {
        return "This element is not uniqueness";
    }
}
