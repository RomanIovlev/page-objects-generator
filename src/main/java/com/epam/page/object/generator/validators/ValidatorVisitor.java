package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.models.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.models.searchrule.CommonSearchRule;
import com.epam.page.object.generator.models.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.models.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.models.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.models.searchrule.FormSearchRule;
import com.epam.page.object.generator.models.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.models.webgroup.FormWebElementGroup;

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
