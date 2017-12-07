package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.RawSearchRule;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationChecker {

    private final static Logger logger = LoggerFactory.getLogger(ValidationChecker.class);

    public void checkRawSearchRules(List<RawSearchRule> rawSearchRules) {
        if (rawSearchRules.stream().anyMatch(RawSearchRule::isInvalid)) {
            StringBuilder stringBuilder = new StringBuilder("\n");

            rawSearchRules.stream()
                .filter(RawSearchRule::isInvalid)
                .forEach(
                    rawSearchRule -> stringBuilder.append(rawSearchRule.getExceptionMessage()));

            logger.error(stringBuilder.toString());
            throw new ValidationException(stringBuilder.toString());
        }
    }

}