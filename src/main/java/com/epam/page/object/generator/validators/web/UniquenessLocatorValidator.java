package com.epam.page.object.generator.validators.web;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationContext;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.util.Pair;

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
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {

        return validationContext.getUrls().stream()
            .map((url -> new Pair<>(url, getCountOfElementsOnWebSite(searchRule, url))))
            .filter(pair -> pair.getValue() > 1)
            .map(pair -> "This " + searchRule.getType() + " element is not unique! On url: "
                + pair.getKey() + " found "
                + pair.getValue() + " elements")
            .collect(Collectors.joining("\n"));
    }

    private int getCountOfElementsOnWebSite(SearchRule searchRule, WebPage url) {
        try {
            return searchRule.extractElementsFromWebSite(url).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
