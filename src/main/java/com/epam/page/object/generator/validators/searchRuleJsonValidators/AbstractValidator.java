package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import com.epam.page.object.generator.model.WebElementGroup;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;

public abstract class AbstractValidator implements ValidatorVisitor {

    @Override
    public ValidationResultNew visit(CommonSearchRule commonSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(ComplexSearchRule complexSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(ComplexInnerSearchRule complexInnerSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(FormSearchRule formSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(FormInnerSearchRule formInnerSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(WebElementGroup webElementGroup) {
        return new ValidationResultNew(true, this + " passed!");
    }
}
