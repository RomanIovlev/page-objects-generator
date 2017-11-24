package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.util.Pair;

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
