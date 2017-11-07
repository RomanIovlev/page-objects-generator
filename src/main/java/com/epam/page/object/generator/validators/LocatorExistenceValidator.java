package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.*;

public class LocatorExistenceValidator implements Validator {

    int order = 0;

    @Override
    public void validate(ValidationContext context) {
        for (SearchRule searchRule : context.getRulesToValidate()) {
            if (isEmpty(searchRule.getCss()) && isEmpty(searchRule.getXpath())) {
                context.addRuleToInvalid(searchRule);
                continue;
            }
            context.addRuleToValid(searchRule);
        }
    }

    @Override
    public int getOrder() {
        return order;
    }
}
