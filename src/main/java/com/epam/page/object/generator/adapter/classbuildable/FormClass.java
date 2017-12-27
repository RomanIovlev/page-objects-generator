package com.epam.page.object.generator.adapter.classbuildable;

import static com.epam.page.object.generator.util.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.*;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

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
 * FormClass allows to generate .java source file from {@link FormWebElementGroup}.
 */
public class FormClass implements JavaClassBuildable {

    private FormWebElementGroup formWebElementGroup;
    private SelectorUtils selectorUtils;

    private final static Logger logger = LoggerFactory.getLogger(FormClass.class);

    public FormClass(FormWebElementGroup formWebElementGroup,
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
            String fieldName = uncapitalize(splitCamelCase(webElement.getUniquenessValue()));
            Class<?> annotationClass = innerSearchRule.getClassAndAnnotation()
                .getElementAnnotation();
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
     * Create {@link JavaAnnotation} for the {@link FormInnerSearchRule}.
     *
     * @param annotationClass class which used for the annotation.
     * @param webElement {@link FormWebElement} which was found on the website.
     * @param searchRule SearchRule for which we are generating annotation.
     * @return {@link JavaAnnotation} which contains all information about future {@link
     * AnnotationSpec}.
     */
    private JavaAnnotation buildAnnotation(Class<?> annotationClass, FormWebElement webElement,
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
     * Get annotation value for the {@link FormInnerSearchRule} by concatenation selector value,
     * uniqueness name and uniqueness value.
     *
     * @param selector {@link Selector} from {@link FormInnerSearchRule}  which contains information
     * about how to find element on the website.
     * @param uniquenessValue value which was found from the website by the 'uniqueness' attribute.
     * @param uniqueness name of the 'uniqueness' attribute.
     * @return value for annotation, which consists of selector value, uniqueness name and
     * uniqueness value.
     * @throws IllegalArgumentException if selector type doesn't support by the application.
     */
    private String getAnnotationValue(Selector selector, String uniquenessValue,
                                      String uniqueness) {
        if (selector.isXpath()) {
            return selectorUtils.resultXpathSelector(selector, uniquenessValue, uniqueness);
        } else if (selector.isCss()) {
            return selectorUtils.resultCssSelector(selector, uniquenessValue, uniqueness);
        }
        throw new IllegalArgumentException("Selector type is unknown " + selector.toString());
    }

    @Override
    public JavaAnnotation buildAnnotation() {
        return null;
    }

    @Override
    public JavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.build(this);
    }

    @Override
    public String toString() {
        return "FormClass with " + formWebElementGroup.getSearchRule();
    }
}
