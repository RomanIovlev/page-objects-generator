package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;

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
    public ValidationResultNew visit(CommonWebElementGroup commonWebElementGroup) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(ComplexWebElementGroup complexWebElementGroup) {
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(FormWebElementGroup formWebElementGroup) {
        return new ValidationResultNew(true, this + " passed!");
    }
}
