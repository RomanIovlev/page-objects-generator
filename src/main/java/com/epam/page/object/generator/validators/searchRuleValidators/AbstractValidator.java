package com.epam.page.object.generator.validators.searchRuleValidators;


import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;

public abstract class AbstractValidator implements ValidatorVisitor{

    @Override
    public ValidationResultNew validate(CommonSearchRule commonSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew validate(ComplexSearchRule complexSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew validate(ComplexInnerSearchRule complexInnerSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew validate(FormSearchRule formSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew validate(FormInnerSearchRule formInnerSearchRule) {
        return new ValidationResultNew(true, this + " passed!");
    }
}
