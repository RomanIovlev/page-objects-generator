package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link DuplicateTitleInInnerSearchRulesValidator} validate that all inner {@link SearchRule} has
 * not any duplicates in titles. <br/> Flag {@link AbstractValidator#isValidateAllSearchRules} =
 * false by default.  <br/> Default priority: 6, must go only after {@link
 * TitleExistenceIntoInnerRulesValidator}.
 */
public class DuplicateTitleInInnerSearchRulesValidator extends AbstractValidator {

    private String duplicate;

    public DuplicateTitleInInnerSearchRulesValidator() {
        super(6, false);
    }

    public DuplicateTitleInInnerSearchRulesValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(6, supportedSearchRuleTypes);
    }

    public DuplicateTitleInInnerSearchRulesValidator(int priority) {
        super(priority, false);
    }

    public DuplicateTitleInInnerSearchRulesValidator(boolean isValidateAllSearchRules) {
        super(6, isValidateAllSearchRules);
    }

    public DuplicateTitleInInnerSearchRulesValidator(int priority,
                                                     boolean isValidateAllSearchRules) {
        super(priority, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        Set<String> titles = new HashSet<>();
        for (SearchRule rule : searchRule.getInnerSearchRules()) {
            if (!titles.add(rule.getTitle())) {
                duplicate = rule.getTitle();
                return false;
            }
        }
        return true;
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {
        return "Title = '" + duplicate + "' is duplicated";
    }
}
