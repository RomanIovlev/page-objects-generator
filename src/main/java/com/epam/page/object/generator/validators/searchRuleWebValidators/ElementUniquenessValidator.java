package com.epam.page.object.generator.validators.searchRuleWebValidators;

import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.AbstractValidator;
import java.util.HashSet;
import java.util.Set;

public class ElementUniquenessValidator extends AbstractValidator {

    @Override
    public ValidationResult visit(CommonWebElementGroup webElementGroup) {
        Set<String> uniquenessValues = new HashSet<>();
        for (WebElement webElement : webElementGroup.getWebElements()) {
            if (!uniquenessValues.add(webElement.getUniquenessValue())) {
                return new ValidationResult(false, "Not unique " + webElementGroup + "!");
            }
        }
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(ComplexWebElementGroup webElementGroup) {
        Set<String> uniquenessValues = new HashSet<>();
        for (WebElement webElement : webElementGroup.getWebElements()) {
            if (!uniquenessValues.add(webElement.getUniquenessValue())) {
                return new ValidationResult(false, "Not unique " + webElementGroup + "!");
            }
        }
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(FormWebElementGroup webElementGroup) {
        Set<String> uniquenessValues = new HashSet<>();
        for (WebElement webElement : webElementGroup.getWebElements()) {
            if (!uniquenessValues.add(webElement.getUniquenessValue())) {
                return new ValidationResult(false, "Not unique " + webElementGroup + "!");
            }
        }
        return new ValidationResult(true, this + " passed!");
    }
}
