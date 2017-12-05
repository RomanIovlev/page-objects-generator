package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.Arrays;
import java.util.Set;

/**
 * {@link TitleOfComplexElementValidator} validate that inner {@link SearchRule} has correct "title"
 * attribute which supported by complex annotation. <br/> Default priority: 7.
 */
public class TitleOfComplexElementValidator extends AbstractValidator {

    public TitleOfComplexElementValidator() {
        super(7);
    }

    public TitleOfComplexElementValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(7, supportedSearchRuleTypes);
    }

    public TitleOfComplexElementValidator(int priority) {
        super(priority);
    }

    public TitleOfComplexElementValidator(int priority, boolean isValidateAllSearchRules) {
        super(priority, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();

        Class elementAnnotation = supportedTypesContainer.getSupportedTypesMap()
            .get(searchRule.getType())
            .getElementAnnotation();

        return searchRule.getInnerSearchRules() == null
            || searchRule.getInnerSearchRules().stream()
            .allMatch(sr -> Arrays.stream(elementAnnotation.getDeclaredMethods())
                .anyMatch(m -> sr.getTitle().equals(m.getName())));
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {
        return "Title: "+searchRule.getTitle()+" is not valid for Type: "+ searchRule.getType();
    }
}