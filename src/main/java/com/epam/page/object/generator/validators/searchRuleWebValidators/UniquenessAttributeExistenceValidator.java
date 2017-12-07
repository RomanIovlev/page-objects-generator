package com.epam.page.object.generator.validators.searchRuleWebValidators;

import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElements.FormWebElement;
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

}
