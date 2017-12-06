package com.epam.page.object.generator.builders;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.*;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.adapter.IJavaAnnotation.AnnotationMember;
import com.epam.page.object.generator.adapter.IJavaField;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.searchRuleFields.ComplexSearchRuleField;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.model.webElements.CommonWebElement;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.webElements.ComplexWebElement;
import com.epam.page.object.generator.model.webElements.FormWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class JavaFieldBuilder {

    private JavaAnnotationBuilder javaAnnotationBuilder;
    private SupportedTypesContainer typesContainer;

    public JavaFieldBuilder(
        JavaAnnotationBuilder javaAnnotationBuilder,
        SupportedTypesContainer typesContainer) {
        this.javaAnnotationBuilder = javaAnnotationBuilder;
        this.typesContainer = typesContainer;
    }

    public List<IJavaField> buildSiteFields(List<WebPage> webPages) {
        List<IJavaField> fields = new ArrayList<>();

        for (WebPage webPage : webPages) {
            String className = firstLetterUp(splitCamelCase(webPage.getTitle()));
            String fieldName = splitCamelCase(webPage.getTitle());

            List<AnnotationMember> pageAnnotations = new ArrayList<>();
            pageAnnotations
                .add(new AnnotationMember("webPage", "$S", webPage.getUrlWithoutDomain()));
            pageAnnotations.add(new AnnotationMember("title", "$S", webPage.getTitle()));
            IJavaAnnotation annotation = new JavaAnnotation(JPage.class, pageAnnotations);
            Modifier[] modifiers = new Modifier[]{PUBLIC, STATIC};

            fields.add(new JavaField(className, fieldName, annotation, modifiers));
        }

        return fields;
    }

    public List<IJavaField> buildWebPageFields(WebPage webPage) {
        List<IJavaField> fields = new ArrayList<>();

        for (WebElementGroup webElementGroup : webPage.getWebElementGroups()) {
            if(webElementGroup.isInvalid()) {
                continue;
            }
            SearchRule searchRule = webElementGroup.getSearchRule();
            List<WebElement> webElements = webElementGroup.getWebElements();

            if (searchRule instanceof CommonSearchRule) {
                List<IJavaField> javaFields = getFields(
                    (CommonSearchRule) searchRule, webElements);
                fields.addAll(javaFields);
            } else if (searchRule instanceof ComplexSearchRule) {
                List<IJavaField> javaFields = getFields(
                    (ComplexSearchRule) searchRule, webElements);
                fields.addAll(javaFields);
            } else if (searchRule instanceof FormSearchRule) {
                JavaField javaField = getField((FormSearchRule) searchRule);
                fields.add(javaField);
            }
        }

        return fields;
    }

    private JavaField getField(FormSearchRule searchRule) {
        String className = typesContainer
            .getSupportedTypesMap().get(searchRule.getTypeName()).getElementClass()
            .getName();
        String fieldName = firstLetterDown(splitCamelCase(searchRule.getSection()));

        Class annotationClass = typesContainer.getSupportedTypesMap()
            .get(searchRule.getTypeName())
            .getElementAnnotation();

        IJavaAnnotation annotation = javaAnnotationBuilder
            .buildAnnotation(annotationClass, searchRule);
        Modifier[] modifiers = new Modifier[]{PUBLIC};

        return new JavaField(className, fieldName, annotation, modifiers);
    }

    private List<IJavaField> getFields(CommonSearchRule searchRule,
                                       List<WebElement> webElements) {
        List<IJavaField> javaFields = new ArrayList<>();

        for (WebElement webElement : webElements) {
            String className = typesContainer
                .getSupportedTypesMap().get(searchRule.getTypeName()).getElementClass().getName();
            String fieldName = firstLetterDown(splitCamelCase(webElement.getUniquenessValue()));

            Class annotationClass = typesContainer.getSupportedTypesMap()
                .get(searchRule.getTypeName())
                .getElementAnnotation();
            IJavaAnnotation annotation = javaAnnotationBuilder
                .buildAnnotation(annotationClass, (CommonWebElement) webElement, searchRule);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            javaFields.add(new JavaField(className, fieldName, annotation, modifiers));
        }

        return javaFields;
    }

    private List<IJavaField> getFields(ComplexSearchRule searchRule,
                                       List<WebElement> webElements) {
        List<IJavaField> javaFields = new ArrayList<>();

        for (WebElement webElement : webElements) {
            String className = typesContainer
                .getSupportedTypesMap().get(searchRule.getTypeName()).getElementClass().getName();
            String fieldName = firstLetterDown(splitCamelCase(webElement.getUniquenessValue()));

            Class annotationClass = typesContainer.getSupportedTypesMap()
                .get(searchRule.getTypeName())
                .getElementAnnotation();
            IJavaAnnotation annotation = javaAnnotationBuilder
                .buildAnnotation(annotationClass, (ComplexWebElement) webElement, searchRule);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            javaFields.add(new JavaField(className, fieldName, annotation, modifiers));
        }

        return javaFields;
    }

    public List<IJavaField> buildFormFields(FormWebElementGroup formWebElementGroup) {
        List<IJavaField> javaFields = new ArrayList<>();

        for (WebElement webElement : formWebElementGroup.getWebElements()) {
            FormInnerSearchRule innerSearchRule = ((FormWebElement) webElement).getSearchRule();
            String className = typesContainer
                .getSupportedTypesMap().get(innerSearchRule.getTypeName()).getElementClass()
                .getName();
            String fieldName = firstLetterDown(splitCamelCase(webElement.getUniquenessValue()));

            Class annotationClass = typesContainer.getSupportedTypesMap()
                .get(innerSearchRule.getTypeName())
                .getElementAnnotation();
            IJavaAnnotation annotation = javaAnnotationBuilder
                .buildAnnotation(annotationClass, (FormWebElement) webElement, innerSearchRule);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            javaFields.add(new JavaField(className, fieldName, annotation, modifiers));
        }

        return javaFields;
    }
}
