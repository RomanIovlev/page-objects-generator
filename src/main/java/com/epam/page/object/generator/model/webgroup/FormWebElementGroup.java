package com.epam.page.object.generator.model.webgroup;

import com.epam.page.object.generator.adapter.AnnotationMember;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.webelement.FormWebElement;
import com.epam.page.object.generator.model.webelement.WebElement;
import com.epam.page.object.generator.validator.ValidationResult;
import com.epam.page.object.generator.validator.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents {@link FormSearchRule} and list of {@link FormWebElement} which was found by this rule
 * from certain {@link WebPage}.
 */
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
    public List<JavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                                  String packageName) {
        return webElementGroupFieldBuilder.build(this, packageName);
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

    /**
     * Returns {@link JavaAnnotation} that represents {@link FormWebElement} in generated class.
     *
     * @param annotationClass annotation class which used for generation annotation.
     * @return {@link JavaAnnotation} that represents {@link FormWebElement} in generated class.
     */
    public JavaAnnotation getAnnotation(Class<?> annotationClass) {
        List<AnnotationMember> annotationMembers = new ArrayList<>();

        annotationMembers.add(new AnnotationMember(searchRule.getSelector().getType(), "$S",
            searchRule.getSelector().getValue()));

        return new JavaAnnotation(annotationClass, annotationMembers);
    }
}
