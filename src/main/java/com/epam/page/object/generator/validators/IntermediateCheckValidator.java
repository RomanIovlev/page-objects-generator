package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;

public class IntermediateCheckValidator implements Validator {

    @Override
    public void validate(ValidationContext validationContext) {
        if (validationContext.hasInvalidRules()) {
            throw new ValidationException(validationContext);
        }
    }

    @Override
    public int getPriority() {
        return 50;
    }



}
