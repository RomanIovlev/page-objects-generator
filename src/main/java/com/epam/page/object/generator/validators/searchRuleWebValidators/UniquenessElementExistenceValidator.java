package com.epam.page.object.generator.validators.searchRuleWebValidators;

import com.epam.page.object.generator.model.WebElementGroup;
import com.epam.page.object.generator.validators.ValidationResultNew;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.AbstractValidator;

public class UniquenessElementExistenceValidator extends AbstractValidator {

    @Override
    public ValidationResultNew visit(WebElementGroup webElementGroup) {
        if(webElementGroup.isUniqueness()){
            return new ValidationResultNew(true, this + " passed!");
        }
        return new ValidationResultNew(false, this + " is not uniqueness!");
    }
}
