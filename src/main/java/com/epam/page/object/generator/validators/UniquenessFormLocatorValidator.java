package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.io.IOException;
import java.util.Set;
import javax.lang.model.element.Element;
import org.jsoup.select.Elements;

public class UniquenessFormLocatorValidator extends AbstractValidator {

    public UniquenessFormLocatorValidator() {
        super(52);
    }

    public UniquenessFormLocatorValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(52, supportedSearchRuleTypes);
    }

    public UniquenessFormLocatorValidator(boolean isValidateAllSearchRules) {
        super(52, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return validationContext.getUrls()
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
        return "Not unique form locator validator";
    }
}
