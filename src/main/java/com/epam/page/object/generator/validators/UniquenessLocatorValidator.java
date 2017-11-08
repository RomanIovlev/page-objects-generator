package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.model.SearchRule;
import java.io.IOException;
import org.jsoup.select.Elements;

public class UniquenessLocatorValidator extends AbstractValidator {

    public UniquenessLocatorValidator() {
        super(2, new NotUniqueSelectorsException("Not unique locator"));
    }

    public UniquenessLocatorValidator(int order, RuntimeException ex) {
        super(order, ex);
    }

    @Override
    public boolean isValid(SearchRule searchRule) {
        if(!getValidationContext().isCheckLocatorsUniqueness()){
            return true;
        }
        boolean isValidInnerSearchRules = true;
        boolean isExistOnUrl = false;

        for (String url : getValidationContext().getUrls()) {
            if (searchRule.getInnerSearchRules() != null) {
                for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
                    if (!isValid(innerSearchRule)) {
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
