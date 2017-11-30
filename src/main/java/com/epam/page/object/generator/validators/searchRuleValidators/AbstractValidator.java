package com.epam.page.object.generator.validators.searchRuleValidators;


import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;

public abstract class AbstractValidator implements ValidatorVisitor{

    @Override
    public boolean validate(CommonSearchRule commonSearchRule) {
        return true;
    }

    @Override
    public boolean validate(ComplexSearchRule complexSearchRule) {
        return true;
    }

    @Override
    public boolean validate(ComplexInnerSearchRule complexInnerSearchRule) {
        return true;
    }

    @Override
    public boolean validate(FormSearchRule formSearchRule) {
        return true;
    }

    @Override
    public boolean validate(FormInnerSearchRule formInnerSearchRule) {
        return true;
    }
}
