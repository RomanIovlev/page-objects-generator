package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import javafx.util.Pair;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * {@link UniquenessLocatorValidator} validate that {@link SearchRule} has uniqueness locator. <br/>
 * Default priority: 51.
 */
public class UniquenessLocatorValidator extends AbstractValidator {

    public UniquenessLocatorValidator() {
        super(52);
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
       // return "This element is not uniqueness";
        return validationContext.getUrls()
                .stream()
                .map(url -> {
                    try {
                        return  new Pair<>(url, searchRule.extractElementsFromWebSite(url).size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(pair -> pair.getValue() > 1)
                .map(pair -> "On url: " + pair.getKey() + " founded " + pair.getValue() + " elements")
                .collect(Collectors.joining("\n"));

    }
}
