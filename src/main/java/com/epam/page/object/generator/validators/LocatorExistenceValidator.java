package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.LocatorExistenceException;
import com.epam.page.object.generator.model.SearchRule;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.*;

public class LocatorExistenceValidator implements Validator {

    private int order = 0;
    private RuntimeException ex = new LocatorExistenceException("No xpath or css locators: ");

    @Override
    public RuntimeException getException() {
        return ex;
    }

    @Override
    public void validate(ValidationContext context) {
        for (SearchRule searchRule : context.getRulesToValidate()) {
            if (isEmpty(searchRule.getCss()) && isEmpty(searchRule.getXpath())) {
                context.addRuleToInvalid(searchRule, getException());
                continue;
            }
        }
    }

    @Override
    public int getOrder() {
        return order;
    }
}
