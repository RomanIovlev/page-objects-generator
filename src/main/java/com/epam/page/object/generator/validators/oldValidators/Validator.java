package com.epam.page.object.generator.validators.oldValidators;

import com.epam.page.object.generator.model.SearchRule;


public interface Validator {

    /**
     * Main method which start validating process.<br/> Input receives: the {@link
     * ValidationContext} from which is taken list of {@link com.epam.page.object.generator.model.SearchRule}
     * which need to visit.<br/> Each {@link com.epam.page.object.generator.model.SearchRule} go
     * throw {@link AbstractValidator#isValid(SearchRule, ValidationContext)} method, which
     * determines the validity of the rule.<br/> At the end of the validation process, {@link
     * ValidationContext} collect all information about validation into his {@link
     * ValidationContext#validationResults}.
     */
    void validate(ValidationContext validationContext);

    /**
     * Method returns current priority of the validator.<br/> It is used for comparator, which sorts
     * the validators by their priority.
     *
     * @return current priority of the validator.
     */
    int getPriority();


}
