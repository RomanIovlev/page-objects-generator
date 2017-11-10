package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;

import java.lang.reflect.Method;

/**
 * {@link TitleOfComplexElementValidator} validate that inner {@link SearchRule} has correct "title"
 * attribute which supported by complex annotation. <br/> Default priority: 2.
 */
public class TitleOfComplexElementValidator extends AbstractValidator {

    public TitleOfComplexElementValidator() {
        super(2);
    }

    public TitleOfComplexElementValidator(int priority) {
        super(priority);
    }

    public TitleOfComplexElementValidator(int priority, boolean isValidateAllSearchRules) {
        super(priority, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        String type = searchRule.getType();
        SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();

        if (searchRule.getInnerSearchRules() == null) {
            return true;
        }

        Class elementAnnotation = supportedTypesContainer.getSupportedTypesMap()
            .get(type)
            .getElementAnnotation();
        for (SearchRule sr : searchRule.getInnerSearchRules()) {
            Boolean valid = false;
            String title = sr.getTitle();

            for (Method m : elementAnnotation.getDeclaredMethods()) {
                if (title.equals(m.getName())) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getExceptionMessage() {
        return "Title is not valid for this Type";
    }
}