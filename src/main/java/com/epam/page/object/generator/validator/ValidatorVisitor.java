package com.epam.page.object.generator.validator;

import com.epam.page.object.generator.model.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;

public interface ValidatorVisitor {

    default ValidationResult visit(CommonSearchRule commonSearchRule){
        return new ValidationResult(true, this + " passed!");
    }

    default ValidationResult visit(ComplexSearchRule complexSearchRule){
        return new ValidationResult(true, this + " passed!");
    }

    default ValidationResult visit(ComplexInnerSearchRule complexInnerSearchRule){
        return new ValidationResult(true, this + " passed!");
    }

    default ValidationResult visit(FormSearchRule formSearchRule){
        return new ValidationResult(true, this + " passed!");
    }

    default ValidationResult visit(FormInnerSearchRule formInnerSearchRule){
        return new ValidationResult(true, this + " passed!");
    }

    default ValidationResult visit(CommonWebElementGroup commonWebElementGroup){
        return new ValidationResult(true, this + " passed!");
    }

    default ValidationResult visit(ComplexWebElementGroup complexWebElementGroup){
        return new ValidationResult(true, this + " passed!");
    }

    default ValidationResult visit(FormWebElementGroup formWebElementGroup){
        return new ValidationResult(true, this + " passed!");
    }
}
