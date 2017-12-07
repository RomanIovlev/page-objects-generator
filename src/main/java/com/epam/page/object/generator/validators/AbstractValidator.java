package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;

public abstract class AbstractValidator implements ValidatorVisitor {

    @Override
    public ValidationResult visit(CommonSearchRule commonSearchRule) {
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(ComplexInnerSearchRule complexInnerSearchRule) {
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(FormSearchRule formSearchRule) {
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(FormInnerSearchRule formInnerSearchRule) {
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(CommonWebElementGroup commonWebElementGroup) {
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(ComplexWebElementGroup complexWebElementGroup) {
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(FormWebElementGroup formWebElementGroup) {
        return new ValidationResult(true, this + " passed!");
    }
}
