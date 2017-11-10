package com.epam.page.object.generator.validators;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.assertj.core.util.Lists;

/**
 * It is a main class which start validation process.<br/> {@link SearchRuleValidator} contains:
 * <ul>
 * <li>Set of the {@link Validator} which will be validate list of {@link
 * com.epam.page.object.generator.model.SearchRule}.</li>
 * <li>{@link ValidationContext} which
 * collect all information about validation process.</li>
 * </ul>
 */
public class SearchRuleValidator {

    /**
     * Set of the {@link Validator} by default, which will be validate list of {@link
     * com.epam.page.object.generator.model.SearchRule}.
     */
    private List<Validator> validators = Lists.newArrayList(
        new LocatorExistenceValidator(),
        new TypeSupportedValidator(),
        new IntermediateCheckValidator());

    private UniquenessLocatorValidator uniquenessLocatorValidator = new UniquenessLocatorValidator();

    private ValidationContext validationContext;

    public SearchRuleValidator(ValidationContext validationContext) {
        this.validationContext = validationContext;
    }

    /**
     * Validation {@link ValidationContext#searchRules} using {@link SearchRuleValidator#validators}.<br/>
     *
     * @throws com.epam.page.object.generator.errors.ValidationException if JSON file is not valid.
     */
    public void validate() throws IOException {

        validators.sort(Comparator.comparingInt(Validator::getPriority));

        for (Validator validator : validators) {
            validator.validate(validationContext);
        }

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
     * Adding a new custom validator to the set of validators.
     *
     * @param validator custom validator.
     */
    public void addValidator(Validator validator) {
        validators.add(validator);
    }
}
