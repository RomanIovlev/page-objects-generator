package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationResult;

public class RootExistenceValidator extends AbstractValidator {

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {
        if (complexSearchRule.getRoot() == null) {
            return new ValidationResult(false,
                "Search rule = " + complexSearchRule + " hasn't got 'root' inner element");
        }

        return new ValidationResult(true, this + " passed!");
    }

}
