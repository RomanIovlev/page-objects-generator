package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Comparator;
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
    private List<Validator> validators = Lists.newArrayList(
        new LocatorExistenceValidator(SearchRuleTypeGroups.allExistingTypes),
        new RootExistenceValidator(SearchRuleTypeGroups.complexTypes),
        new TitleExistenceIntoInnerRulesValidator(SearchRuleTypeGroups.complexTypes),
        new UniquenessAttributeExistenceIntoComplexRuleValidator(SearchRuleTypeGroups.complexTypes),
        new DuplicateTitleInInnerSearchRulesValidator(SearchRuleTypeGroups.complexTypes),
        new TitleOfComplexElementValidator(SearchRuleTypeGroups.complexTypes),
        new IntermediateCheckValidator(),
        new UrlsValidator(),
        new UniquenessAttributeExistenceValidator(SearchRuleTypeGroups.allExistingTypes),
        new SectionAttributeExistenceValidator(SearchRuleTypeGroups.formAndSectionTypes));

    private UniquenessLocatorValidator uniquenessLocatorValidator =
        new UniquenessLocatorValidator(SearchRuleTypeGroups.commonAndComplexTypes);

    private UniquenessFormLocatorValidator uniquenessFormLocatorValidator =
        new UniquenessFormLocatorValidator(SearchRuleTypeGroups.formAndSectionTypes);

    private ValidationContext validationContext;

    public ValidatorsStarter(SupportedTypesContainer supportedTypesContainer) {
        validators.add(new TypeSupportedValidator(Sets.newHashSet(SearchRuleType.ALL),
            supportedTypesContainer));
        validators.add(new FormTypeValidator(SearchRuleTypeGroups.formAndSectionTypes,
            supportedTypesContainer));
    }

    public ValidatorsStarter(List<Validator> newValidators,
                             SupportedTypesContainer supportedTypesContainer) {
        this(supportedTypesContainer);
        if (newValidators != null) {
            validators.addAll(newValidators);
        }
    }

    /**
     * Validation {@link ValidationContext#searchRules} using {@link ValidatorsStarter#validators}.<br/>
     *
     * @throws com.epam.page.object.generator.errors.ValidationException if JSON file is not valid.
     */
    public List<SearchRule> validate(List<SearchRule> searchRules, List<String> urls)
        throws IOException {

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
                validators.add(uniquenessFormLocatorValidator);
            }
        } else {
            validators.remove(uniquenessLocatorValidator);
            validators.remove(uniquenessFormLocatorValidator);
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
     *
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
     *
     * @return list of {@link Validator}
     */
    public List<Validator> getValidators() {
        return validators;
    }
}
