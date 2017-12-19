package com.epam.page.object.generator.adapter.classbuildable;

import static com.epam.page.object.generator.util.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.util.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.*;

import com.epam.page.object.generator.adapter.AnnotationMember;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.model.webelement.FormWebElement;
import com.epam.page.object.generator.model.webelement.WebElement;
import com.epam.page.object.generator.util.SelectorUtils;
import com.squareup.javapoet.AnnotationSpec;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link FormClassBuildable} allows to generate .java source file from {@link
 * FormWebElementGroup}.
 */
public class FormClassBuildable implements JavaClassBuildable {

    private FormWebElementGroup formWebElementGroup;
    private SelectorUtils selectorUtils;

    private final static Logger logger = LoggerFactory.getLogger(FormClassBuildable.class);

    public FormClassBuildable(FormWebElementGroup formWebElementGroup,
                              SelectorUtils selectorUtils) {
        this.formWebElementGroup = formWebElementGroup;
        this.selectorUtils = selectorUtils;
    }

    public FormWebElementGroup getFormWebElementGroup() {
        return formWebElementGroup;
    }

    @Override
    public List<JavaField> buildFields(String packageName) {
        List<JavaField> javaFields = new ArrayList<>();

        for (WebElement webElement : formWebElementGroup.getWebElements()) {
            FormInnerSearchRule innerSearchRule = ((FormWebElement) webElement).getSearchRule();
            logger.debug("Add field found by SearchRule" + innerSearchRule);

            String fullClassName = innerSearchRule.getClassAndAnnotation().getElementClass()
                .getName();
            String fieldName = firstLetterDown(splitCamelCase(webElement.getUniquenessValue()));
            Class annotationClass = innerSearchRule.getClassAndAnnotation().getElementAnnotation();
            logger.debug("Start creating annotation...");
            JavaAnnotation annotation = buildAnnotation(annotationClass,
                (FormWebElement) webElement, innerSearchRule);
            logger.debug("Finish creating annotation");
            Modifier[] modifiers = new Modifier[]{PUBLIC};

            JavaField javaField = new JavaField(fullClassName, fieldName, annotation, modifiers);
            javaFields.add(javaField);
            logger.debug("Add field = " + javaField);
            logger.debug("Finish SearchRule" + innerSearchRule + "\n");
        }

        return javaFields;
    }

    /**
     * Create {@link JavaAnnotation} for the specific SearchRule.
     *
     * @param annotationClass class which used for the annotation.
     * @param webElement element which was found on the website.
     * @param searchRule SearchRule for which we are generating annotation.
     * @return {@link JavaAnnotation} which contains all information about future {@link
     * AnnotationSpec}
     */
    private JavaAnnotation buildAnnotation(Class annotationClass, FormWebElement webElement,
                                           FormInnerSearchRule searchRule) {
        List<AnnotationMember> annotationMembers = new ArrayList<>();

        String uniquenessValue = webElement.getUniquenessValue();
        Selector selector = searchRule.getTransformedSelector();
        String annotationValue = getAnnotationValue(selector, uniquenessValue,
            searchRule.getUniqueness());

        annotationMembers.add(new AnnotationMember(selector.getType(), "$S", annotationValue));
        return new JavaAnnotation(annotationClass, annotationMembers);
    }

    /**
     * Get annotation value for the SearchRule by concatenation selector value, uniqueness name and
     * uniqueness value.
     *
     * @param selector {@link Selector} from SearchRule which contains information about how to find
     * element on the website
     * @param uniquenessValue value which was found from the website by the 'uniqueness' attribute
     * @param uniqueness name of the 'uniqueness' attribute
     */
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
    public JavaAnnotation buildAnnotation() {
        return null;
    }

    @Override
    public JavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.visit(this);
    }

    @Override
    public String toString() {
        return "FormClassBuildable with " + formWebElementGroup.getSearchRule();
    }
}
