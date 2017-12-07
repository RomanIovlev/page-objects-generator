package com.epam.page.object.generator.adapter.javaClassBuildable;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.*;

import com.epam.page.object.generator.adapter.javaAnnotations.AnnotationMember;
import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.IJavaField;
import com.epam.page.object.generator.adapter.javaAnnotations.JavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.JavaField;
import com.epam.page.object.generator.adapter.javaClasses.IJavaClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElements.FormWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class FormClass implements JavaClassBuildable {

    private FormWebElementGroup formWebElementGroup;

    public FormClass(FormWebElementGroup formWebElementGroup) {
        this.formWebElementGroup = formWebElementGroup;
    }

    public FormWebElementGroup getFormWebElementGroup() {
        return formWebElementGroup;
    }

    @Override
    public List<IJavaField> getFields(String packageName) {
        List<IJavaField> javaFields = new ArrayList<>();

        for (WebElement webElement : formWebElementGroup.getWebElements()) {
            FormInnerSearchRule innerSearchRule = ((FormWebElement) webElement).getSearchRule();

            String fullClassName = innerSearchRule.getClassAndAnnotation().getElementClass()
                .getName();
            String fieldName = firstLetterDown(splitCamelCase(webElement.getUniquenessValue()));
            Class annotationClass = innerSearchRule.getClassAndAnnotation().getElementAnnotation();
            IJavaAnnotation annotation = buildAnnotation(annotationClass,
                (FormWebElement) webElement, innerSearchRule);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            javaFields.add(new JavaField(fullClassName, fieldName, annotation, modifiers));
        }

        return javaFields;
    }

    private IJavaAnnotation buildAnnotation(Class annotationClass, FormWebElement webElement,
                                            FormInnerSearchRule searchRule) {
        List<AnnotationMember> annotationMembers = new ArrayList<>();

        String uniquenessValue = webElement.getUniquenessValue();
        Selector selector = searchRule.getTransformedSelector();
        String annotationValue = getAnnotationValue(selector, uniquenessValue,
            searchRule.getUniqueness());

        annotationMembers.add(new AnnotationMember(selector.getType(), "$S", annotationValue));

        return new JavaAnnotation(annotationClass, annotationMembers);
    }

    private String getAnnotationValue(Selector selector, String uniquenessValue,
                                      String uniqueness) {
        if (selector.isXpath()) {
            return resultXpathSelector(selector, uniquenessValue, uniqueness);
        } else if (selector.isCss()) {
            return resultCssSelector(selector, uniquenessValue, uniqueness);
        }
        return null;
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        return null;
    }

    @Override
    public IJavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.visit(this);
    }
}
