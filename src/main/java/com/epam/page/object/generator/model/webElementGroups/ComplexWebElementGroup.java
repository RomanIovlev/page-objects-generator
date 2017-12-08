package com.epam.page.object.generator.model.webElementGroups;

import com.epam.page.object.generator.adapter.javaAnnotations.AnnotationMember;
import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.IJavaField;
import com.epam.page.object.generator.adapter.javaAnnotations.JavaAnnotation;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.validators.ValidationResult;
import com.epam.page.object.generator.validators.ValidatorVisitor;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.support.FindBy;

public class ComplexWebElementGroup implements WebElementGroup {

    private ComplexSearchRule searchRule;
    private List<WebElement> webElements;
    private SelectorUtils selectorUtils;

    private List<ValidationResult> validationResults = new ArrayList<>();

    public ComplexWebElementGroup(ComplexSearchRule searchRule, List<WebElement> webElements,
                                  SelectorUtils selectorUtils) {
        this.searchRule = searchRule;
        this.webElements = webElements;
        this.selectorUtils = selectorUtils;
    }

    @Override
    public ComplexSearchRule getSearchRule() {
        return searchRule;
    }

    @Override
    public List<WebElement> getWebElements() {
        return webElements;
    }

    @Override
    public List<IJavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                                   String packageName) {
        return webElementGroupFieldBuilder.visit(this);
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

    public IJavaAnnotation getAnnotation(Class annotationClass, WebElement webElement) {
        List<AnnotationMember> annotationMembers = new ArrayList<>();

        for (ComplexInnerSearchRule innerSearchRule : searchRule.getInnerSearchRules()) {

            String annotationElementName = innerSearchRule.getTitle();
            IJavaAnnotation innerSearchRuleAnnotation;

            if (annotationElementName.equals("root")) {
                Selector selector = innerSearchRule.getTransformedSelector();
                String uniquenessValue = webElement.getUniquenessValue();
                String annotationValue = getAnnotationValue(selector, uniquenessValue,
                    searchRule.getUniqueness());

                List<AnnotationMember> innerAnnotation = new ArrayList<>();
                innerAnnotation
                    .add(new AnnotationMember(selector.getType(), "$S", annotationValue));

                innerSearchRuleAnnotation = new JavaAnnotation(FindBy.class, innerAnnotation);
            } else {
                Selector selector = innerSearchRule.getSelector();

                List<AnnotationMember> innerAnnotation = new ArrayList<>();
                innerAnnotation
                    .add(new AnnotationMember(selector.getType(), "$S", selector.getValue()));

                innerSearchRuleAnnotation = new JavaAnnotation(FindBy.class, innerAnnotation);
            }

            annotationMembers
                .add(new AnnotationMember(annotationElementName, "$L",
                    innerSearchRuleAnnotation));
        }

        return new JavaAnnotation(annotationClass, annotationMembers);
    }

    private String getAnnotationValue(Selector selector, String uniquenessValue,
                                      String uniqueness) {
        if (selector.isXpath()) {
            return selectorUtils.resultXpathSelector(selector, uniquenessValue, uniqueness);
        } else if (selector.isCss()) {
            return selectorUtils.resultCssSelector(selector, uniquenessValue, uniqueness);
        }
        return null;
    }
}
