package com.epam.page.object.generator.validators.searchRuleValidators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.ValidationResultNew;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TitleOfComplexElementValidator extends AbstractValidator {

    private Class elementAnnotation;
    private ComplexSearchRule complexSearchRule;

    @Override
    public ValidationResultNew validate(ComplexInnerSearchRule complexInnerSearchRule) {
        if (Arrays.stream(elementAnnotation.getDeclaredMethods())
            .anyMatch(m -> complexInnerSearchRule.getTitle().equals(m.getName()))) {
            return new ValidationResultNew(true, this + " passed!");
        }

        return new ValidationResultNew(false,
            complexInnerSearchRule + " is not supported in " + complexSearchRule + "!");
    }

    @Override
    public ValidationResultNew validate(ComplexSearchRule complexSearchRule) {
        SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();

        elementAnnotation = supportedTypesContainer.getSupportedTypesMap()
            .get(complexSearchRule.getType())
            .getElementAnnotation();

        this.complexSearchRule = complexSearchRule;

        return

    }


}
