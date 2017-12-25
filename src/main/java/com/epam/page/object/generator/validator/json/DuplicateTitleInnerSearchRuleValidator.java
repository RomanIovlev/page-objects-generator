package com.epam.page.object.generator.validator.json;

import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.validator.ValidationResult;
import com.epam.page.object.generator.validator.ValidatorVisitor;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator only for {@link ComplexSearchRule}. It's checking that there is no duplicated titles
 * within searchRule For all others types of searchRule it will always return passed
 * ValidationResult.
 */
public class DuplicateTitleInnerSearchRuleValidator implements ValidatorVisitor {

    private final static Logger logger = LoggerFactory
        .getLogger(DuplicateTitleInnerSearchRuleValidator.class);

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {
        logger.debug("Start validate " + complexSearchRule);
        Set<String> titles = new HashSet<>();
        for (ComplexInnerSearchRule searchRule : complexSearchRule.getInnerSearchRules()) {
            if (!titles.add(searchRule.getTitle())) {
                logger.warn("Title = '" + searchRule.getTitle() + "' is duplicated!");
                logger.warn("Invalid " + complexSearchRule + "\n");
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
