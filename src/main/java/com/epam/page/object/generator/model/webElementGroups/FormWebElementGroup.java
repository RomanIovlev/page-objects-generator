package com.epam.page.object.generator.model.webElementGroups;

import com.epam.page.object.generator.adapter.javaAnnotations.AnnotationMember;
import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.IJavaField;
import com.epam.page.object.generator.adapter.javaAnnotations.JavaAnnotation;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;

public class FormWebElementGroup implements WebElementGroup {

    private FormSearchRule searchRule;
    private List<WebElement> webElements;

    private List<ValidationResult> validationResults = new ArrayList<>();

    public FormWebElementGroup(FormSearchRule searchRule, List<WebElement> webElements) {
        this.searchRule = searchRule;
        this.webElements = webElements;
    }

    @Override
    public FormSearchRule getSearchRule() {
        return searchRule;
    }

    @Override
    public List<WebElement> getWebElements() {
        return webElements;
    }

    @Override
    public List<IJavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                                   String packageName) {
        return webElementGroupFieldBuilder.visit(this, packageName);
    }

    @Override
    public void accept(ValidatorVisitor validatorVisitor) {
        validationResults.add(validatorVisitor.visit(this));
    }

    @Override
    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }

    @Override
    public boolean isValid() {
        return validationResults.stream().allMatch(ValidationResult::isValid);
    }

    @Override
    public boolean isInvalid() {
        return validationResults.stream()
            .anyMatch(validationResultNew -> !validationResultNew.isValid());
    }

    @Override
    public String toString() {
        return searchRule.toString();
    }

    public IJavaAnnotation getAnnotation(Class annotationClass) {
        List<AnnotationMember> annotationMembers = new ArrayList<>();

        annotationMembers.add(new AnnotationMember(searchRule.getSelector().getType(), "$S",
            searchRule.getSelector().getValue()));

        return new JavaAnnotation(annotationClass, annotationMembers);
    }
}
