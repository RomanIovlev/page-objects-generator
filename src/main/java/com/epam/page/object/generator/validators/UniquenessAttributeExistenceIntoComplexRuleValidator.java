package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import java.util.Optional;

/**
 * {@link UniquenessAttributeExistenceIntoComplexRuleValidator} validate that complex {@link
 * SearchRule} has only one 'uniqueness' attribute and only 'root' inner element has it. <br/>
 * Default priority: 4.
 */
public class UniquenessAttributeExistenceIntoComplexRuleValidator extends AbstractValidator {

    public UniquenessAttributeExistenceIntoComplexRuleValidator() {
        super(4);
    }

    public UniquenessAttributeExistenceIntoComplexRuleValidator(int order) {
        super(order);
    }

    public UniquenessAttributeExistenceIntoComplexRuleValidator(
        boolean isValidateAllSearchRules) {
        super(4, isValidateAllSearchRules);
    }

    public UniquenessAttributeExistenceIntoComplexRuleValidator(int order,
                                                                boolean isValidateAllSearchRules) {
        super(order, isValidateAllSearchRules);
    }

    @Override
    public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
        Optional<SearchRule> rootInnerElement = searchRule.getRootInnerRule();

        boolean isUniquenessAttributePresentsOnce = searchRule.getInnerSearchRules()
            .stream()
            .filter(o -> o.getUniqueness() != null)
            .count() == 1;

        boolean hasRootElementUniquenessAttribute =
            rootInnerElement.isPresent() && rootInnerElement.get().getUniqueness() != null;

        return isUniquenessAttributePresentsOnce && hasRootElementUniquenessAttribute;
    }

    @Override
    public String getExceptionMessage(SearchRule searchRule, ValidationContext validationContext) {
        return "Only 'root' inner search rule can has got 'uniqueness' attribute";
    }
}
