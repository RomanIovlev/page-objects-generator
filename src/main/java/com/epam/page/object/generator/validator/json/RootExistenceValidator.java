package com.epam.page.object.generator.validator.json;

import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.validator.ValidationResult;
import com.epam.page.object.generator.validator.ValidatorVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootExistenceValidator implements ValidatorVisitor {

    private final static Logger logger = LoggerFactory.getLogger(RootExistenceValidator.class);

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {
        logger.debug("Start validate " + complexSearchRule);
        if (complexSearchRule.getRoot() == null) {
            logger.warn("'root' title is absent in " + complexSearchRule);
            logger.warn("Invalid " +complexSearchRule + "\n");
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
