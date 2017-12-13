package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.util.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.util.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.util.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.adapter.annotation.IJavaAnnotation;
import com.epam.page.object.generator.adapter.filed.IJavaField;
import com.epam.page.object.generator.adapter.filed.JavaField;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.model.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.model.webelement.WebElement;
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
            IJavaAnnotation annotation = commonWebElementGroup
                .getAnnotation(annotationClass, webElement);
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
            IJavaAnnotation annotation = complexWebElementGroup
                .getAnnotation(annotationClass, webElement);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            javaFields.add(new JavaField(className, fieldName, annotation, modifiers));
        }

        return javaFields;
    }

    public List<IJavaField> visit(FormWebElementGroup formWebElementGroup,
                                  String packageName) {
        FormSearchRule searchRule = formWebElementGroup.getSearchRule();

        String className = packageName + ".form." + firstLetterUp(searchRule.getSection());
        String fieldName = firstLetterDown(searchRule.getSection());
        Class annotationClass = searchRule.getClassAndAnnotation().getElementAnnotation();
        IJavaAnnotation annotation = formWebElementGroup.getAnnotation(annotationClass);
        Modifier[] modifiers = new Modifier[]{PUBLIC};

        return Collections
            .singletonList(new JavaField(className, fieldName, annotation, modifiers));
    }
}
