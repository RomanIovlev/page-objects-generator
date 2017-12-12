package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.models.searchrule.CommonSearchRule;
import com.epam.page.object.generator.models.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.models.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.models.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.models.searchrule.FormSearchRule;
import com.epam.page.object.generator.models.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.models.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.models.webgroup.FormWebElementGroup;

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
