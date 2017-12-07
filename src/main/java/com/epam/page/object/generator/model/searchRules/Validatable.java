package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.List;

public interface Validatable {

    void accept(ValidatorVisitor validatorVisitor);

    List<ValidationResult> getValidationResults();

    boolean isValid();

    boolean isInvalid();
}
