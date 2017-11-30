package com.epam.page.object.generator.model.searchRules;


import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;

public interface Validatable {
    boolean beValidated(ValidatorVisitor validatorVisitor);

}
