package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * AbstractValidator is the abstract base class for crating a new Validator,
 * which can be used into SearchRuleValidator.
 * <p>
 * For creating a new Validator you need:
 * <ol>
 * <li>Create a new class which extends from {@link AbstractValidator} and override
 * the main method {@link AbstractValidator#isValid(SearchRule, ValidationContext)}.</li>
 * <li>Create default constructor with {@link AbstractValidator#priority}.</li>
 * <li>Override {@link AbstractValidator#getExceptionMessage()}.</li>
 * </ol>
 */
public abstract class AbstractValidator implements Validator {

    /**
     * Validator priority is a number which specifies the order of executing validators.
     * All validators which present by the default have some default priority, but if you create
     * your own validator you must not forget to set priority for you validator into default constructor
     * and change priories for all default validators if you need. <br/><br/>
     * <p>
     * For priority you need to use int numbers: <br/>
     * <ul>
     * <li>0-49 fot validators which validate format of JSON files</li>
     * <li>51+ for validators which validate SearchRules by the urls</li>
     * </ul>
     *
     * For example: <br/>
     * UniquenessLocatorValidator can have priority equals 51,
     * because it checks that the SearchRule is uniqueness on the WebPage by the url.
     * It can be like this:<br/>
     * {@code
     *  public UniquenessLocatorValidator() {
     *      super(51);
     *  }
     * }
     */
    private int priority;

    /**
     * Each validator can work in two modes. <br/>
     * <ul>
     * <li>isValidateAllSearchRules = true<br/>
     * The validator validates all SearchRules every time.</li>
     * <li>isValidateAllSearchRules = false (default)<br/>
     * The validator validates only SearchRules that passed the previous validator.</li>
     * </ul>
     */
    private boolean isValidateAllSearchRules = false;

    public AbstractValidator(int priority) {
        this.priority = priority;
    }

    public AbstractValidator(int priority, boolean isValidateAllSearchRules) {
        this.priority = priority;
        this.isValidateAllSearchRules = isValidateAllSearchRules;
    }

    public boolean isInnerRulesValid(SearchRule searchRule, ValidationContext validationContext) {
        if (searchRule.getInnerSearchRules() != null) {
            for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
                if (!isValid(innerSearchRule, validationContext)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void validate(ValidationContext validationContext) {

        List<SearchRule> searchRules = new ArrayList<>();
        searchRules.addAll(isValidateAllSearchRules ? validationContext.getAllSearchRules()
            : validationContext.getValidRules());

        searchRules.forEach(searchRule -> {
            validationContext
                .addValidationResult(
                    !isValid(searchRule, validationContext) ? new ValidationResult(false, this,
                        searchRule) : new ValidationResult(true, this, searchRule));
        });
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setIsValidateAllSearchRules(boolean flag) {
        this.isValidateAllSearchRules = flag;
    }

    /**
     * This method checks the validity of the {@link SearchRule}.<br/><br/>
     * <p>
     * For example (this method check if {@link SearchRule} to have at least one locator: xpath or css):<br/>
     * {@code
     *  public boolean isValid(SearchRule searchRule, ValidationContext validationContext) {
     *      return !isEmpty(searchRule.getCss()) || !isEmpty(searchRule.getXpath());
     *  }
     * }
     *
     * @param searchRule        searchRule that we validation at the moment.
     * @param validationContext context for our validators, which contains all {@link ValidationResult} about validation process.
     * @return <b>true</b> - if {@link SearchRule} is valid<br/> <b>false</b> - if {@link SearchRule} is not valid.
     */
    public abstract boolean isValid(SearchRule searchRule, ValidationContext validationContext);

}
