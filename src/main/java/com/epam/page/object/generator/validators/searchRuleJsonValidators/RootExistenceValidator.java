package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootExistenceValidator extends AbstractValidator {

    private final static Logger logger = LoggerFactory.getLogger(RootExistenceValidator.class);

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {
        logger.debug("Start validate " + complexSearchRule);
        if (complexSearchRule.getRoot() == null) {
            logger.debug("'root' title is absent in " + complexSearchRule);
            logger.debug("Invalid " +complexSearchRule + "\n");
            return new ValidationResult(false,
                "Search rule = " + complexSearchRule + " hasn't got 'root' inner element");
        }
        logger.debug("Find title = 'root' " + complexSearchRule.getRoot());
        logger.debug("Valid " + complexSearchRule + "\n");
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public String toString() {
        return "RootExistenceValidator";
    }
}
