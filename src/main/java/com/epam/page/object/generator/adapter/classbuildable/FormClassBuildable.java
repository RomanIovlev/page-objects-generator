package com.epam.page.object.generator.adapter.classbuildable;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.*;

import com.epam.page.object.generator.adapter.annotations.AnnotationMember;
import com.epam.page.object.generator.adapter.annotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.fileds.IJavaField;
import com.epam.page.object.generator.adapter.annotations.JavaAnnotation;
import com.epam.page.object.generator.adapter.fileds.JavaField;
import com.epam.page.object.generator.adapter.classes.IJavaClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.models.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.models.webelement.FormWebElement;
import com.epam.page.object.generator.models.webelement.WebElement;
import com.epam.page.object.generator.utils.SelectorUtils;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class FormClassBuildable implements JavaClassBuildable {

    private FormWebElementGroup formWebElementGroup;
    private SelectorUtils selectorUtils;

    public FormClassBuildable(FormWebElementGroup formWebElementGroup,
                              SelectorUtils selectorUtils) {
        this.formWebElementGroup = formWebElementGroup;
        this.selectorUtils = selectorUtils;
    }

    public FormWebElementGroup getFormWebElementGroup() {
        return formWebElementGroup;
    }

    @Override
    public List<IJavaField> buildFields(String packageName) {
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
            return selectorUtils.resultXpathSelector(selector, uniquenessValue, uniqueness);
        } else if (selector.isCss()) {
            return selectorUtils.resultCssSelector(selector, uniquenessValue, uniqueness);
        }
        return null;
    }

    @Override
    public IJavaAnnotation buildAnnotation() {
        return null;
    }

    @Override
    public IJavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.visit(this);
    }
}
