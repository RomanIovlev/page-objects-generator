package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.squareup.javapoet.ClassName;
import java.io.IOException;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;

public class FormField implements JavaField {
    private SearchRule searchRule;
    private Element element;
    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformation xpathToCssTransformation;

    public FormField(SearchRule searchRule, Element element,
                     SupportedTypesContainer typesContainer,
                     XpathToCssTransformation xpathToCssTransformation) {
        this.searchRule = searchRule;
        this.element = element;
        this.typesContainer = typesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    @Override
    public Class getFieldClass() {
        return typesContainer.getSupportedTypesMap().get(searchRule.getType()).getElementClass();
    }

    @Override
    public ClassName getFieldClassName() {
        return null;
    }

    @Override
    public JavaAnnotation getAnnotation() {
        Class fieldAnnotationClass = typesContainer.getSupportedTypesMap().get(searchRule.getType())
            .getElementAnnotation();
        if (SearchRuleTypeGroups.isCommonType(searchRule)) {
            return  new CommonAnnotation(searchRule, element,
                fieldAnnotationClass, xpathToCssTransformation);
        } else if (SearchRuleTypeGroups.isComplexType(searchRule)) {
            return new ComplexAnnotation(searchRule, element, xpathToCssTransformation);
        } else if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
            return new FormOrSectionAnnotation(searchRule,
                fieldAnnotationClass);
        } else {
            //This type of search rule does not supported
            throw new UnsupportedOperationException(searchRule.getType()
                + " search rule type does not supported");
        }
    }

    @Override
    public String getFieldName() throws IOException {
        String elementRequiredValue;
        if (SearchRuleTypeGroups.isCommonType(searchRule)) {
            elementRequiredValue = searchRule.getRequiredValueFromFoundElement(element);
         } else if (SearchRuleTypeGroups.isComplexType(searchRule)) {
            if (searchRule.getRootInnerRule().isPresent()) {
                elementRequiredValue = searchRule.getRootInnerRule().get()
                    .getRequiredValueFromFoundElement(element);
            } else {
                elementRequiredValue = searchRule.getType();
            }
        } else if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
            elementRequiredValue = searchRule.getSection();
        } else {
            //This type of search rule does not supported
            throw new UnsupportedOperationException(searchRule.getType()
                + " search rule type does not supported");
        }

        return firstLetterDown(splitCamelCase(elementRequiredValue));
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
