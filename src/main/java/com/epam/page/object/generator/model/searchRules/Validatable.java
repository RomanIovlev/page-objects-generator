package com.epam.page.object.generator.model.searchRules;


import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.List;

public interface Validatable {
    void accept(ValidatorVisitor validatorVisitor);
    List<ValidationResultNew> getValidationResults();
    boolean isValid();
    boolean isInvalid();
    void addValidationResult(ValidationResultNew validationResult);
}
