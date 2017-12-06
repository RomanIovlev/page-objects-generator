package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.searchRules.Validatable;
import com.epam.page.object.generator.validators.oldValidators.ValidationContext;
import com.epam.page.object.generator.validators.oldValidators.Validator;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.DuplicateTitleInnerSearchRuleValidator;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.RootExistenceValidator;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.TitleOfComplexElementValidator;
import java.io.IOException;
import java.util.List;
import org.assertj.core.util.Lists;

/**
 * It is a main class which start validation process.<br/> {@link JsonValidators} contains: <ul>
 * <li>Set of the {@link Validator} which will be visit list of {@link
 * com.epam.page.object.generator.model.SearchRule}.</li> <li>{@link ValidationContext} which
 * collect all information about validation process.</li> </ul>
 */
public class JsonValidators {

    /**
     * Set of the {@link Validator} by default, which will be visit list of {@link
     * com.epam.page.object.generator.model.SearchRule}.
     */
    private List<ValidatorVisitor> validators = Lists.newArrayList(
        new DuplicateTitleInnerSearchRuleValidator(),
        new RootExistenceValidator(),
        new TitleOfComplexElementValidator()
    );

    public JsonValidators() {
    }

    public JsonValidators(List<ValidatorVisitor> newValidators) {
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }

    /**
     * Validation {@link ValidationContext#searchRules} using {@link JsonValidators#validators}.<br/>
     *
     * @throws com.epam.page.object.generator.errors.ValidationException if JSON file is not valid.
     */
    public void validate(List<SearchRule> searchRules){

        for (ValidatorVisitor validator : validators) {
            for (Validatable searchRule : searchRules) {
                searchRule.accept(validator);
            }
        }
    }

    /**
     * Adding a new custom validator to the list of validators.
     *
     * @param validator {@link Validator}
     */
    public void addValidator(ValidatorVisitor validator) {
        validators.add(validator);
    }

    /**
     * Adding a new list of custom validators to the list of validators.
     *
     * @param newValidators list of {@link Validator}
     */

    public void addValidatorsList(List<ValidatorVisitor> newValidators) {
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }
}
