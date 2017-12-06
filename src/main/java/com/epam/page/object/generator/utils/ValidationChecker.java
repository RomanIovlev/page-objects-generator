package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.RawSearchRule;
import java.util.List;

public class ValidationChecker {

    public void checkRawSearchRules(List<RawSearchRule> rawSearchRules) {
        if (rawSearchRules.stream().anyMatch(RawSearchRule::isInvalid)) {
            StringBuilder stringBuilder = new StringBuilder("\n");

            rawSearchRules.stream()
                .filter(RawSearchRule::isInvalid)
                .forEach(
                    rawSearchRule -> stringBuilder.append(rawSearchRule.getExceptionMessage()));

            throw new ValidationException(stringBuilder.toString());
        }
    }

}