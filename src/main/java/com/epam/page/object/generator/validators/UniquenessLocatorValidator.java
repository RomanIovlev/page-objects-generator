package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import java.io.IOException;
import org.jsoup.select.Elements;

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
        for (String url : validationContext.getUrls()) {

            if (!isInnerRulesValid(searchRule, validationContext)) {
                return false;
            }

            try {
                Elements elements = searchRule.extractElementsFromWebSite(url);
                if (elements.size() > 1) {
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
