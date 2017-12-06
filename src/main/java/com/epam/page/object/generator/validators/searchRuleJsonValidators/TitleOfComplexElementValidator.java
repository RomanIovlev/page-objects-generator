package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationResultNew;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TitleOfComplexElementValidator extends AbstractValidator {

    @Override
    public ValidationResultNew visit(ComplexSearchRule complexSearchRule) {
        SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();

        Class elementAnnotation = supportedTypesContainer.getSupportedTypesMap()
            .get(complexSearchRule.getTypeName())
            .getElementAnnotation();

        StringBuilder stringBuilder = new StringBuilder();

        Method[] declaredMethods = elementAnnotation.getDeclaredMethods();

        for (ComplexInnerSearchRule complexInnerSearchRule : complexSearchRule
            .getInnerSearchRules()) {
            if (Arrays.stream(declaredMethods)
                .noneMatch(method -> complexInnerSearchRule.getTitle().equals(method.getName()))) {
                stringBuilder.append("Title: ").append(complexInnerSearchRule.getTitle())
                    .append(" is not valid for Type: ").append(complexSearchRule.getType())
                    .append("\n");
            }
        }

        if (stringBuilder.length() == 0) {
            return new ValidationResultNew(true, this + " passed!");
        }

        return new ValidationResultNew(false, stringBuilder.toString());
    }


}
