package com.epam.page.object.generator.validators.searchRuleValidators;


import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;

public interface ValidatorVisitor {
    boolean validate(CommonSearchRule commonSearchRule);
    boolean validate(ComplexSearchRule complexSearchRule);
    boolean validate(ComplexInnerSearchRule complexInnerSearchRule);
    boolean validate(FormSearchRule formSearchRule);
    boolean validate(FormInnerSearchRule formInnerSearchRule);
}
