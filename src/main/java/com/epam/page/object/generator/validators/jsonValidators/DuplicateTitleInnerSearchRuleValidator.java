package com.epam.page.object.generator.validators.jsonValidators;

import com.epam.page.object.generator.models.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.models.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationResult;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicateTitleInnerSearchRuleValidator extends AbstractValidator {

    private final static Logger logger = LoggerFactory.getLogger(DuplicateTitleInnerSearchRuleValidator.class);

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {
        logger.debug("Start validate " + complexSearchRule);
        Set<String> titles = new HashSet<>();
        for (ComplexInnerSearchRule searchRule : complexSearchRule.getInnerSearchRules()) {
            if (!titles.add(searchRule.getTitle())) {
                logger.debug("Title = '" + searchRule.getTitle() + "' is duplicated!");
                logger.debug("Invalid " +complexSearchRule + "\n");
                return new ValidationResult(false,
                    "Title = '" + searchRule.getTitle() + "' are duplicated");
            }
            logger.debug("Add title = '" + searchRule.getTitle() + "'");
        }
        logger.debug("Valid " + complexSearchRule + "\n");
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public String toString() {
        return "DuplicateTitleInnerSearchRuleValidator";
    }
}
