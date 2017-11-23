package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import java.io.IOException;
import javafx.util.Pair;
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

        return validationContext.getUrls().stream()
            .map((url -> new Pair<>(url, getCountOfElementsOnWebSite(searchRule, url))))
            .filter(pair -> pair.getValue() > 1)
            .map(pair -> "This " + searchRule.getType() + " element is not unique! On url: "
                + pair.getKey() + " found "
                + pair.getValue() + " elements")
            .collect(Collectors.joining("\n"));
    }

    private int getCountOfElementsOnWebSite(SearchRule searchRule, String url) {
        try {
            return searchRule.extractElementsFromWebSite(url).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
