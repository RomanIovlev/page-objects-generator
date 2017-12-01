package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.searchRules.Validatable;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.epam.page.object.generator.validators.oldValidators.ValidationContext;
import com.epam.page.object.generator.validators.oldValidators.Validator;
import com.epam.page.object.generator.validators.searchRuleValidators.DuplicateTitleInnerSearchRuleValidator;
import com.epam.page.object.generator.validators.searchRuleValidators.RootExistenceValidator;
import com.epam.page.object.generator.validators.searchRuleValidators.ValidatorVisitor;
import com.epam.page.object.generator.validators.web.UniquenessFormLocatorValidator;
import com.epam.page.object.generator.validators.web.UniquenessLocatorValidator;
import java.io.IOException;
import java.util.List;
import org.assertj.core.util.Lists;

/**
 * It is a main class which start validation process.<br/> {@link ValidatorsStarter} contains: <ul>
 * <li>Set of the {@link Validator} which will be validate list of {@link
 * com.epam.page.object.generator.model.SearchRule}.</li> <li>{@link ValidationContext} which
 * collect all information about validation process.</li> </ul>
 */
public class ValidatorsStarter {

    /**
     * Set of the {@link Validator} by default, which will be validate list of {@link
     * com.epam.page.object.generator.model.SearchRule}.
     */
    private List<ValidatorVisitor> validators = Lists.newArrayList(
        new DuplicateTitleInnerSearchRuleValidator(),
        new RootExistenceValidator()
    );

    private UniquenessLocatorValidator uniquenessLocatorValidator =
        new UniquenessLocatorValidator(SearchRuleTypeGroups.commonAndComplexTypes);

    private UniquenessFormLocatorValidator uniquenessFormLocatorValidator =
        new UniquenessFormLocatorValidator(SearchRuleTypeGroups.formAndSectionTypes);

    public ValidatorsStarter(List<ValidatorVisitor> newValidators) {
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }

    /**
     * Validation {@link ValidationContext#searchRules} using {@link ValidatorsStarter#validators}.<br/>
     *
     * @throws com.epam.page.object.generator.errors.ValidationException if JSON file is not valid.
     */
    public List<SearchRule> validate(List<SearchRule> searchRules)
        throws IOException {

        for (ValidatorVisitor validator : validators) {
            for (Validatable searchRule : searchRules) {
                searchRule.beValidated(validator);
            }
        }

        return searchRules;
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
