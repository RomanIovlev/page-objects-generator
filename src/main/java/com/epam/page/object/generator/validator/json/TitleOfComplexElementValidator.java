package com.epam.page.object.generator.validator.json;

import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.validator.AbstractValidator;
import com.epam.page.object.generator.validator.ValidationResult;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TitleOfComplexElementValidator extends AbstractValidator {

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {

        Class elementAnnotation = complexSearchRule.getClassAndAnnotation().getElementAnnotation();

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
            return new ValidationResult(true, this + " passed!");
        }

        return new ValidationResult(false, stringBuilder.toString());
    }

    @Override
    public String toString() {
        return "TitleOfComplexElementValidator";
    }
}
