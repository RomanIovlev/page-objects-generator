package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import java.io.IOException;
import org.jsoup.select.Elements;

/**
 * {@link UniquenessLocatorValidator} validate that {@link SearchRule} has uniqueness locator. <br/>
 * Default priority: 51.
 */
public class UniquenessLocatorValidator extends AbstractValidator {

    public UniquenessLocatorValidator() {
        super(51);
    }

    public UniquenessLocatorValidator(int order) {
        super(order);
    }

    public UniquenessLocatorValidator(boolean isValidateAllSearchRules) {
        super(51, isValidateAllSearchRules);
    }

    public UniquenessLocatorValidator(int order, boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        if (!isInnerRulesValid(searchRule, validationContext)) {
            return false;
        }
        for (String url : validationContext.getUrls()) {
            try {
                if (searchRule.extractElementsFromWebSite(url).size() > 1) {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public String getExceptionMessage() {
        return "This element is not uniqueness";
    }
}
