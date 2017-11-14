package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import org.assertj.core.util.Lists;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * It is a main class which start validation process.<br/> {@link ValidatorsStarter} contains:
 * <ul>
 * <li>Set of the {@link Validator} which will be validate list of {@link
 * com.epam.page.object.generator.model.SearchRule}.</li>
 * <li>{@link ValidationContext} which
 * collect all information about validation process.</li>
 * </ul>
 */
public class ValidatorsStarter {

    /**
     * Set of the {@link Validator} by default, which will be validate list of {@link
     * com.epam.page.object.generator.model.SearchRule}.
     */
    private List<Validator> validators = Lists.newArrayList(
        new LocatorExistenceValidator(),
        new TypeSupportedValidator(),
        new IntermediateCheckValidator(),
        new TitleOfComplexElementValidator(),
        new UrlsValidator());

    private UniquenessLocatorValidator uniquenessLocatorValidator =
            new UniquenessLocatorValidator();

    private ValidationContext validationContext;

    public ValidatorsStarter() {
    }

    public ValidatorsStarter(List<Validator> newValidators) {
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }

    /**
     * Validation {@link ValidationContext#searchRules} using {@link ValidatorsStarter#validators}.<br/>
     *
     * @throws com.epam.page.object.generator.errors.ValidationException if JSON file is not valid.
     */
    public List<SearchRule> validate(List<SearchRule> searchRules, List<String> urls) throws IOException {

        validationContext = new ValidationContext(searchRules, urls);

        validators.sort(Comparator.comparingInt(Validator::getPriority));

        for (Validator validator : validators) {
            validator.validate(validationContext);
        }

        return validationContext.getValidRules();
    }

    /**
     * Allows you to enable or disable {@link UniquenessLocatorValidator}.
     *
     * @param checkLocatorsUniqueness <b>true</b> - enable validator | <b>false</b> - disable
     * validator.
     */
    public void setCheckLocatorsUniqueness(boolean checkLocatorsUniqueness) {
        if (checkLocatorsUniqueness) {
            if (!validators.contains(uniquenessLocatorValidator)) {
                validators.add(uniquenessLocatorValidator);
            }
        } else {
            validators.remove(uniquenessLocatorValidator);
        }
    }

    /**
     * Adding a new custom validator to the list of validators.
     *
     * @param validator {@link Validator}
     */
    public void addValidator(Validator validator) {
        validators.add(validator);
    }

    /**
     * Adding a new list of custom validators to the list of validators.
     * @param newValidators list of {@link Validator}
     */

    public void addValidatorsList(List<Validator> newValidators) {
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }

    /**
     * Return {@link ValidationContext} which used by {@link ValidatorsStarter}.
     *
     * @return {@link ValidationContext}
     */
    public ValidationContext getValidationContext() {
        return validationContext;
    }

    /**
     * Return list of {@link Validator} which used by {@link ValidatorsStarter}
     * @return list of {@link Validator}
     */
    public List<Validator> getValidators() {
        return validators;
    }
}
