package com.epam.page.object.generator.validators.searchRuleValidators;

import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;
import java.util.HashSet;
import java.util.Set;

public class DuplicateTitleInnerSearchRuleValidator extends AbstractValidator {

    @Override
    public ValidationResultNew validate(ComplexSearchRule complexSearchRule) {
        Set<String> titles = new HashSet<>();
        for (ComplexInnerSearchRule searchRule : complexSearchRule.getInnerSearchRules()) {
            if (!titles.add(searchRule.getTitle())) {
                return new ValidationResultNew(false, "Title = " + searchRule.getTitle() + " are duplicated");
            }
        }
        return new ValidationResultNew(true, this + " passed!");

    }

}
