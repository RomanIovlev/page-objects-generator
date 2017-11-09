package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;

import java.lang.reflect.Method;

public class TitleOfComplexElementValiadator extends AbstractValidator{

    public TitleOfComplexElementValiadator() {
        super(3);
    }

    public TitleOfComplexElementValiadator(int priority) {
        super(priority);
    }

    public TitleOfComplexElementValiadator(int priority, boolean isValidateAllSearchRules) {
        super(priority, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
       String title = searchRule.getTitle();
       String type = searchRule.getType();
        SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();

        Class elementAnnotation = supportedTypesContainer.getSupportedTypesMap()
                .get(type)
                .getElementAnnotation();

        for (Method m: elementAnnotation.getDeclaredMethods()) {
            if(title.equals(m.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getExceptionMessage() {
        return "Title is not valid for this Type";
    }
}