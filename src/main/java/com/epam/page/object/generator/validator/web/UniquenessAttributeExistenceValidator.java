package com.epam.page.object.generator.validator.web;

import com.epam.page.object.generator.model.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.model.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.model.webelement.FormWebElement;
import com.epam.page.object.generator.model.webgroup.WebElementGroup;
import com.epam.page.object.generator.validator.ValidationResult;
import com.epam.page.object.generator.validator.ValidatorVisitor;

/**
 * Validate that {@link WebElementGroup} has existence 'uniqueness' attribute.
 */
public class UniquenessAttributeExistenceValidator implements ValidatorVisitor {

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
