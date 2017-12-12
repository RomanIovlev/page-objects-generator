package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.searchRules.SearchRule;
import com.epam.page.object.generator.models.searchRules.Validatable;
import com.epam.page.object.generator.validators.ValidationResult;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationChecker {

    private final static Logger logger = LoggerFactory.getLogger(ValidationChecker.class);

    public void checkRawSearchRules(List<RawSearchRule> rawSearchRules) {
        if (rawSearchRules.stream().anyMatch(RawSearchRule::isInvalid)) {
            StringBuilder stringBuilder = new StringBuilder(
                "\nJson file has invalid search rules:\n");

            rawSearchRules.stream()
                .filter(RawSearchRule::isInvalid)
                .forEach(
                    rawSearchRule -> stringBuilder.append("\n").append(rawSearchRule)
                        .append(" is invalid:\n").append(rawSearchRule.getExceptionMessage()));

            logger.error(stringBuilder.toString());
            throw new ValidationException(stringBuilder.toString());
        }
    }

    public void checkSearchRules(List<SearchRule> searchRuleList) {
        if (searchRuleList.stream().anyMatch(Validatable::isInvalid)) {
            StringBuilder stringBuilder = new StringBuilder("\n");

            searchRuleList.stream()
                .filter(Validatable::isInvalid)
                .forEach(
                    validatable ->
                        validatable.getValidationResults().stream().filter(
                            ValidationResult::isInvalid).forEach(
                            validationResult -> stringBuilder.append(validationResult.getReason()))
                );

            logger.error(stringBuilder.toString());
            throw new ValidationException(stringBuilder.toString());
        }
    }
}