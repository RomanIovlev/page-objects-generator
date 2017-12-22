package com.epam.page.object.generator.validator.web;

import com.epam.page.object.generator.model.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.model.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.model.webelement.WebElement;
import com.epam.page.object.generator.validator.ValidationResult;
import com.epam.page.object.generator.validator.ValidatorVisitor;
import java.util.HashSet;
import java.util.Set;

public class ElementUniquenessValidator implements ValidatorVisitor {

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

    @Override
    public String toString() {
        return "ElementUniquenessValidator";
    }
}
