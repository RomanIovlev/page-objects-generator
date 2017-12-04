package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import com.epam.page.object.generator.model.WebElementGroup;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;

public interface ValidatorVisitor {

    ValidationResultNew visit(CommonSearchRule commonSearchRule);

    ValidationResultNew visit(ComplexSearchRule complexSearchRule);

    ValidationResultNew visit(ComplexInnerSearchRule complexInnerSearchRule);

    ValidationResultNew visit(FormSearchRule formSearchRule);

    ValidationResultNew visit(FormInnerSearchRule formInnerSearchRule);

    ValidationResultNew visit(WebElementGroup webElementGroup);
}
