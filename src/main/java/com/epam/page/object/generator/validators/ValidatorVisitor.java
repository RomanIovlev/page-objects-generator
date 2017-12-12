package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.models.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.models.searchRules.CommonSearchRule;
import com.epam.page.object.generator.models.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.models.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.models.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.models.searchRules.FormSearchRule;
import com.epam.page.object.generator.models.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.models.webElementGroups.FormWebElementGroup;

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
