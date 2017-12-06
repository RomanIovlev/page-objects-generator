package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationResultNew;
import java.util.HashSet;
import java.util.Set;

public class DuplicateTitleInnerSearchRuleValidator extends AbstractValidator {

    @Override
    public ValidationResultNew visit(ComplexSearchRule complexSearchRule) {
        Set<String> titles = new HashSet<>();
        for (ComplexInnerSearchRule searchRule : complexSearchRule.getInnerSearchRules()) {
            if (!titles.add(searchRule.getTitle())) {
                return new ValidationResultNew(false,
                    "Title = " + searchRule.getTitle() + " are duplicated");
            }
        }
        return new ValidationResultNew(true, this + " passed!");
    }

}
