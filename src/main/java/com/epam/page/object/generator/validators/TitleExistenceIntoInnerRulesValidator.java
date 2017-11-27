package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

/**
 * {@link TitleExistenceIntoInnerRulesValidator} validate that all inner rules into complex {@link
 * SearchRule} has 'title' attribute.<br/> Default priority: 5.
 */
public class TitleExistenceIntoInnerRulesValidator extends AbstractValidator {

    public TitleExistenceIntoInnerRulesValidator() {
        super(5);
    }

    public TitleExistenceIntoInnerRulesValidator(int priority) {
        super(priority);
    }

    public TitleExistenceIntoInnerRulesValidator(boolean isValidateAllSearchRules) {
        super(5, isValidateAllSearchRules);
    }

    public TitleExistenceIntoInnerRulesValidator(int priority, boolean isValidateAllSearchRules) {
        super(priority, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return searchRule.getInnerSearchRules().stream()
            .allMatch(innerSearchRule -> innerSearchRule.getTitle() != null);
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {
        return "Inner rule 'title' attribute missing";
    }
}
