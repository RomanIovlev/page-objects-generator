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

    ValidationResultNew visit(CommonSearchRule commonSearchRule);

    ValidationResultNew visit(ComplexSearchRule complexSearchRule);

    ValidationResultNew visit(ComplexInnerSearchRule complexInnerSearchRule);

    ValidationResultNew visit(FormSearchRule formSearchRule);

    ValidationResultNew visit(FormInnerSearchRule formInnerSearchRule);

    ValidationResultNew visit(CommonWebElementGroup commonWebElementGroup);

    ValidationResultNew visit(ComplexWebElementGroup complexWebElementGroup);

    ValidationResultNew visit(FormWebElementGroup formWebElementGroup);
}
