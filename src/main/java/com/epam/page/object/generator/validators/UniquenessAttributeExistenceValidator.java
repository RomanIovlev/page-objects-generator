package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.io.IOException;
import java.util.Set;
import org.jsoup.select.Elements;

/**
 * {@link UniquenessAttributeExistenceValidator} validate that {@link SearchRule} has correct unique
 * attribute. <br/> Default priority: 52.
 */
public class UniquenessAttributeExistenceValidator extends AbstractValidator {

    public UniquenessAttributeExistenceValidator() {
        super(52);
    }

    public UniquenessAttributeExistenceValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(52, supportedSearchRuleTypes);
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
                    Elements elements = searchRule.extractElementsFromWebSite(url);
                    if (searchRule.getTitle() == null) {
                        return !searchRule.getRequiredValueFromFoundElement(elements).isEmpty();
                    } else {
                        if (searchRule.getTitle().equals("root")) {
                            return !searchRule.getRequiredValueFromFoundElement(elements).isEmpty();
                        }
                        return true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            });
        }

        return isInnerRulesValid(searchRule, validationContext);
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {

        return "Uniqueness: " + searchRule.getUniqueness() + " attribute does not exist";
    }
}
