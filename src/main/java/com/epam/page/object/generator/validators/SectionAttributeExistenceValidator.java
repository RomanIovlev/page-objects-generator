package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.Set;

public class SectionAttributeExistenceValidator extends AbstractValidator {

    public SectionAttributeExistenceValidator() {
        super(23);
    }

    public SectionAttributeExistenceValidator(Set<SearchRuleType> supportedSearchRuleTypes) {
        super(23, supportedSearchRuleTypes);
    }

    public SectionAttributeExistenceValidator(boolean isValidateAllSearchRules) {
        super(23, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        return searchRule.getSection() != null && !searchRule.getSection().isEmpty();
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {
        return "Section = '" + searchRule.getSection()
            + "' is not allowed as section attribute name";
    }

}
