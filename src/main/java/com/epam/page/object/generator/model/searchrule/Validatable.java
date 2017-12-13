package com.epam.page.object.generator.model.searchrule;

import com.epam.page.object.generator.validator.ValidationResult;
import com.epam.page.object.generator.validator.ValidatorVisitor;
import java.util.List;

public interface Validatable {

    void accept(ValidatorVisitor validatorVisitor);

    List<ValidationResult> getValidationResults();

    boolean isValid();

    boolean isInvalid();
}
