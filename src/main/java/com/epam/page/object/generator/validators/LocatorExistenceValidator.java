package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.LocatorExistenceException;
import com.epam.page.object.generator.model.SearchRule;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.*;

public class LocatorExistenceValidator extends AbstractValidator {

    private int order;
    private RuntimeException ex;

    public LocatorExistenceValidator() {
        super(0, new LocatorExistenceException("No xpath or css"));
    }

    public LocatorExistenceValidator(int order, RuntimeException ex) {
        super(order, ex);
    }


    @Override
    public boolean validate(SearchRule searchRule) {
        boolean isValidInnerSearchRules = true;
        if (searchRule.getInnerSearchRules() != null) {
            for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
                if (!validate(innerSearchRule)) {
                    isValidInnerSearchRules = false;
                    break;
                }
            }
        }
        return isValidInnerSearchRules && (!isEmpty(searchRule.getCss()) || !isEmpty(searchRule.getXpath()));
    }
}
