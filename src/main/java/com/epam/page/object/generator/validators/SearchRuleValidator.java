package com.epam.page.object.generator.validators;

import static java.lang.String.format;

import com.epam.page.object.generator.builder.FieldsBuilder;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.errors.ValidationException.ErrorCode;
import com.epam.page.object.generator.model.SearchRule;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SearchRuleValidator {

    private static Set<String> supportedTypes = FieldsBuilder.getSupportedTypes();

    public static void validate(List<SearchRule> rules) throws ValidationException {
        List<SearchRule> incorrectRules = new ArrayList<>();

        for (Iterator<SearchRule> iter = rules.iterator(); iter.hasNext();) {
            SearchRule rule = iter.next();

            if (!supportedTypes.contains(rule.getType().toLowerCase())) {
                incorrectRules.add(rule);
                iter.remove();
            }
        }
        if (!incorrectRules.isEmpty()) {
            throw new ValidationException(ErrorCode.NOT_SPECIFIED,
                format("Unsupported elements. Supported types: %s. Incorrect rules:%s",
                    supportedTypes, incorrectRules));
        }
    }

}