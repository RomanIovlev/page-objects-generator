package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;

public class RootExistenceValidator extends AbstractValidator {

    @Override
    public ValidationResultNew visit(ComplexSearchRule complexSearchRule) {
        if (complexSearchRule.getRoot() == null) {
            return new ValidationResultNew(false,
                "Search rule = " + complexSearchRule + " hasn't got 'root' inner element");
        }

        return new ValidationResultNew(true, this + " passed!");
    }

}
