package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.model.SearchRule;
import java.io.IOException;
import org.jsoup.select.Elements;

public class UniquenessLocatorValidator extends AbstractValidator {

    public UniquenessLocatorValidator() {
        super(2);
    }

    public UniquenessLocatorValidator(int order, RuntimeException ex) {
        super(order);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        boolean isValidInnerSearchRules = true;
        boolean isExistOnUrl = false;

        for (String url : validationContext.getUrls()) {
            if (searchRule.getInnerSearchRules() != null) {
                for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
                    if (!isValid(innerSearchRule, validationContext)) {
                        isValidInnerSearchRules = false;
                        break;
                    }
                }
            }
            try {
                Elements elements = searchRule.extractElementsFromWebSite(url);
                if(elements.size() > 1){
                    isExistOnUrl = false;
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return isValidInnerSearchRules && isExistOnUrl;
    }
}
