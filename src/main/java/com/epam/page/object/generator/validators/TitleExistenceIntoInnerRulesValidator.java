package com.epam.page.object.generator.validators;

import com.codeborne.selenide.commands.Val;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.Set;

/**
 * {@link TitleExistenceIntoInnerRulesValidator} validate that all inner rules into complex {@link
 * SearchRule} has 'title' attribute.<br/>
 * Default priority: 3.
 */
public class TitleExistenceIntoInnerRulesValidator extends AbstractValidator {

    public TitleExistenceIntoInnerRulesValidator() {
        super(3);
    }

    public TitleExistenceIntoInnerRulesValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(3, supportedSearchRuleTypes);
    }

    public TitleExistenceIntoInnerRulesValidator(int priority) {
        super(priority);
    }

    public TitleExistenceIntoInnerRulesValidator(boolean isValidateAllSearchRules) {
        super(3, isValidateAllSearchRules);
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
