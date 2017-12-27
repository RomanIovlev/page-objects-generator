package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.util.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.model.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.model.webelement.WebElement;
import com.epam.page.object.generator.model.webgroup.WebElementGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebElementGroupFieldBuilder knows how to build list of {@link JavaField} from the {@link
 * WebElementGroup}.<br/>
 *
 * This class based on Visitor pattern. If you want to add a new group of elements, you need to
 * create a new class which will implement {@link WebElementGroup} interface and than create new
 * build(NewWebElementGroup newWebElementGroup) method in WebElementGroupFieldBuilder which will
 * take your NewWebElementGroup as a parameter.
 */
public class WebElementGroupFieldBuilder {

    private final static Logger logger = LoggerFactory.getLogger(WebElementGroupFieldBuilder.class);

    public List<JavaField> build(CommonWebElementGroup commonWebElementGroup) {
        List<JavaField> javaFields = new ArrayList<>();
        CommonSearchRule searchRule = commonWebElementGroup.getSearchRule();

        logger.debug("Add fields found by " + searchRule);
        for (WebElement webElement : commonWebElementGroup.getWebElements()) {
            String className = searchRule.getClassAndAnnotation().getElementClass().getName();
            String fieldName = uncapitalize(splitCamelCase(webElement.getUniquenessValue()));
            Class<?> annotationClass = searchRule.getClassAndAnnotation().getElementAnnotation();
            JavaAnnotation annotation = commonWebElementGroup
                .getAnnotation(annotationClass, webElement);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            JavaField javaField = new JavaField(className, fieldName, annotation, modifiers);
            javaFields.add(javaField);
            logger.debug("Add field = " + javaField);
        }
        logger.debug("Finish " + searchRule + "\n");

        return javaFields;
    }

    public List<JavaField> build(ComplexWebElementGroup complexWebElementGroup) {
        List<JavaField> javaFields = new ArrayList<>();
        ComplexSearchRule searchRule = complexWebElementGroup.getSearchRule();

        logger.debug("Add fields found by " + searchRule);
        for (WebElement webElement : complexWebElementGroup.getWebElements()) {
            String className = searchRule.getClassAndAnnotation().getElementClass().getName();
            String fieldName = uncapitalize(splitCamelCase(webElement.getUniquenessValue()));
            Class<?> annotationClass = searchRule.getClassAndAnnotation().getElementAnnotation();
            JavaAnnotation annotation = complexWebElementGroup
                .getAnnotation(annotationClass, webElement);
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            JavaField javaField = new JavaField(className, fieldName, annotation, modifiers);
            javaFields.add(javaField);
            logger.debug("Add field = " + javaField);
        }
        logger.debug("Finish " + searchRule + "\n");

        return javaFields;
    }

    public List<JavaField> build(FormWebElementGroup formWebElementGroup,
                                 String packageName) {
        FormSearchRule searchRule = formWebElementGroup.getSearchRule();

        logger.debug("Add field found by " + searchRule);
        String className = packageName + ".form." + capitalize(searchRule.getSection());
        String fieldName = uncapitalize(searchRule.getSection());
        Class<?> annotationClass = searchRule.getClassAndAnnotation().getElementAnnotation();
        JavaAnnotation annotation = formWebElementGroup.getAnnotation(annotationClass);
        Modifier[] modifiers = new Modifier[]{PUBLIC};

        JavaField javaField = new JavaField(className, fieldName, annotation, modifiers);
        logger.debug("Add field = " + javaField);
        logger.debug("Finish " + searchRule + "\n");
        return Collections.singletonList(javaField);
    }
}
