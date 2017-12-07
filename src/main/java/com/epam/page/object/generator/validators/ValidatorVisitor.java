package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;

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
