package com.epam.page.object.generator.validators.searchRuleWebValidators;

import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElements.FormWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.validators.AbstractValidator;
import com.epam.page.object.generator.validators.ValidationResultNew;
import java.util.HashSet;
import java.util.Set;

public class UniquenessAttributeExistenceValidator extends AbstractValidator {

    @Override
    public ValidationResultNew visit(CommonWebElementGroup webElementGroup) {
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
            return new ValidationResultNew(false, builder.toString());
        }
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(ComplexWebElementGroup webElementGroup) {
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
            return new ValidationResultNew(false, builder.toString());
        }
        return new ValidationResultNew(true, this + " passed!");
    }

    @Override
    public ValidationResultNew visit(FormWebElementGroup webElementGroup) {
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
            return new ValidationResultNew(false, builder.toString());
        }
        return new ValidationResultNew(true, this + " passed!");
    }

}
