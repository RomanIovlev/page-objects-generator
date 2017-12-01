package com.epam.page.object.generator.model.searchRules;


import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import java.util.List;

public interface Validatable {
    ValidationResultNew beValidated(ValidatorVisitor validatorVisitor);
    List<ValidationResultNew> getValidationResults();
    boolean isValid();
    boolean isInvalid();
}
