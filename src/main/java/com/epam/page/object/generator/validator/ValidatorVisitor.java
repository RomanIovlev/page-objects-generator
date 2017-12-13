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

    ValidationResult visit(CommonSearchRule commonSearchRule);

    ValidationResult visit(ComplexSearchRule complexSearchRule);

    ValidationResult visit(ComplexInnerSearchRule complexInnerSearchRule);

    ValidationResult visit(FormSearchRule formSearchRule);

    ValidationResult visit(FormInnerSearchRule formInnerSearchRule);

    ValidationResult visit(CommonWebElementGroup commonWebElementGroup);

    ValidationResult visit(ComplexWebElementGroup complexWebElementGroup);

    ValidationResult visit(FormWebElementGroup formWebElementGroup);
}
