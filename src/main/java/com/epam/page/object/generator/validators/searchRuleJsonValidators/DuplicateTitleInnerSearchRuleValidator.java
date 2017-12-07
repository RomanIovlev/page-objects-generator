package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationResult;
import java.util.HashSet;
import java.util.Set;

public class DuplicateTitleInnerSearchRuleValidator extends AbstractValidator {

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {
        Set<String> titles = new HashSet<>();
        for (ComplexInnerSearchRule searchRule : complexSearchRule.getInnerSearchRules()) {
            if (!titles.add(searchRule.getTitle())) {
                return new ValidationResult(false,
                    "Title = " + searchRule.getTitle() + " are duplicated");
            }
        }
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public String toString() {
        return "DuplicateTitleInnerSearchRuleValidator";
    }
}
