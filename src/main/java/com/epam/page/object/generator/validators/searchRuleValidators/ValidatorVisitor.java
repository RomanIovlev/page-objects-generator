package com.epam.page.object.generator.validators.searchRuleValidators;


import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;

public interface ValidatorVisitor {
    ValidationResultNew validate(CommonSearchRule commonSearchRule);
    ValidationResultNew validate(ComplexSearchRule complexSearchRule);
    ValidationResultNew validate(ComplexInnerSearchRule complexInnerSearchRule);
    ValidationResultNew validate(FormSearchRule formSearchRule);
    ValidationResultNew validate(FormInnerSearchRule formInnerSearchRule);
}
