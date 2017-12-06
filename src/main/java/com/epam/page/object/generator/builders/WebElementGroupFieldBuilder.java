package com.epam.page.object.generator.builders;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.adapter.IJavaField;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElements.CommonWebElement;
import com.epam.page.object.generator.model.webElements.ComplexWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Modifier;

public class WebElementGroupFieldBuilder {

    public List<IJavaField> visit(CommonWebElementGroup commonWebElementGroup) {
        List<IJavaField> javaFields = new ArrayList<>();
        CommonSearchRule searchRule = commonWebElementGroup.getSearchRule();

        for (WebElement webElement : commonWebElementGroup.getWebElements()) {
            String className = searchRule.getClassAndAnnotation().getElementClass().getName();
            String fieldName = firstLetterDown(splitCamelCase(webElement.getUniquenessValue()));
            Class annotationClass = searchRule.getClassAndAnnotation().getElementAnnotation();
            IJavaAnnotation annotation = commonWebElementGroup.getAnnotation(annotationClass, webElement);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            javaFields.add(new JavaField(className, fieldName, annotation, modifiers));
        }

        return javaFields;
    }

    public List<IJavaField> visit(ComplexWebElementGroup complexWebElementGroup) {
        List<IJavaField> javaFields = new ArrayList<>();
        ComplexSearchRule searchRule = complexWebElementGroup.getSearchRule();

        for (WebElement webElement : complexWebElementGroup.getWebElements()) {
            String className = searchRule.getClassAndAnnotation().getElementClass().getName();
            String fieldName = firstLetterDown(splitCamelCase(webElement.getUniquenessValue()));
            Class annotationClass = searchRule.getClassAndAnnotation().getElementAnnotation();
            IJavaAnnotation annotation = complexWebElementGroup.getAnnotation(annotationClass, webElement);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            javaFields.add(new JavaField(className, fieldName, annotation, modifiers));
        }

        return javaFields;
    }

    public List<IJavaField> visit(FormWebElementGroup formWebElementGroup) {
        FormSearchRule searchRule = formWebElementGroup.getSearchRule();

        String className = searchRule.getClassAndAnnotation().getElementClass().getName();
        String fieldName = firstLetterDown(splitCamelCase(searchRule.getSection()));
        Class annotationClass = searchRule.getClassAndAnnotation().getElementAnnotation();
        IJavaAnnotation annotation = formWebElementGroup.getAnnotation(annotationClass);
        Modifier[] modifiers = new Modifier[]{PUBLIC};

        return Collections
            .singletonList(new JavaField(className, fieldName, annotation, modifiers));
    }
}
