package com.epam.page.object.generator.validators.webValidators;

import com.epam.page.object.generator.models.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.models.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.models.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.models.webElements.FormWebElement;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationResult;

public class UniquenessAttributeExistenceValidator extends AbstractValidator {

    @Override
    public ValidationResult visit(CommonWebElementGroup webElementGroup) {
        if (webElementGroup.getWebElements().stream()
            .anyMatch(webElement -> webElement.getUniquenessValue().equals(""))) {
            StringBuilder builder = new StringBuilder();
            webElementGroup.getWebElements().stream()
                .filter(webElement -> webElement.getUniquenessValue().equals(""))
                .forEach(webElement -> builder
                    .append("Attribute 'uniqueness' = '")
                    .append(webElementGroup.getSearchRule().getUniqueness())
                    .append("' does not exist in ").append(webElement)
                    .append("!\n"));
            return new ValidationResult(false, builder.toString());
        }
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(ComplexWebElementGroup webElementGroup) {
        if (webElementGroup.getWebElements().stream()
            .anyMatch(webElement -> webElement.getUniquenessValue().equals(""))) {
            StringBuilder builder = new StringBuilder();
            webElementGroup.getWebElements().stream()
                .filter(webElement -> webElement.getUniquenessValue().equals(""))
                .forEach(webElement -> builder
                    .append("Attribute 'uniqueness' = '")
                    .append(webElementGroup.getSearchRule().getUniqueness())
                    .append("' does not exist in ").append(webElement)
                    .append("!\n"));
            return new ValidationResult(false, builder.toString());
        }
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public ValidationResult visit(FormWebElementGroup webElementGroup) {
        if (webElementGroup.getWebElements().stream()
            .anyMatch(webElement -> webElement.getUniquenessValue().equals(""))) {
            StringBuilder builder = new StringBuilder();
            webElementGroup.getWebElements().stream()
                .filter(webElement -> webElement.getUniquenessValue().equals(""))
                .forEach(webElement -> builder
                    .append("Attribute 'uniqueness' = '")
                    .append(((FormWebElement) webElement).getSearchRule().getUniqueness())
                    .append("' does not exist in ").append(webElement)
                    .append("!\n"));
            return new ValidationResult(false, builder.toString());
        }
        return new ValidationResult(true, this + " passed!");
    }

    @Override
    public String toString() {
        return "UniquenessAttributeExistenceValidator";
    }
}
