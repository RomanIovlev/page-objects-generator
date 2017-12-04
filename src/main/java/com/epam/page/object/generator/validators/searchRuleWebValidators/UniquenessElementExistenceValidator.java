package com.epam.page.object.generator.validators.searchRuleWebValidators;

import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.AbstractValidator;

public class UniquenessElementExistenceValidator extends AbstractValidator {

    @Override
    public ValidationResultNew visit(CommonSearchRule commonSearchRule) {
        return super.visit(commonSearchRule);
    }

    @Override
    public ValidationResultNew visit(ComplexSearchRule complexSearchRule) {
        return super.visit(complexSearchRule);
    }

    @Override
    public ValidationResultNew visit(ComplexInnerSearchRule complexInnerSearchRule) {
        return super.visit(complexInnerSearchRule);
    }

    @Override
    public ValidationResultNew visit(FormSearchRule formSearchRule) {
        return super.visit(formSearchRule);
    }

    @Override
    public ValidationResultNew visit(FormInnerSearchRule formInnerSearchRule) {
        return super.visit(formInnerSearchRule);
    }
}
