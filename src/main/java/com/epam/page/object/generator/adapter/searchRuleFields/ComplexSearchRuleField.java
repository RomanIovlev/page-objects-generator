package com.epam.page.object.generator.adapter.searchRuleFields;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.adapter.searchRuleAnnotations.ComplexSearchRuleAnnotation;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;

public class ComplexSearchRuleField implements JavaField {

    private ComplexSearchRule searchRule;
    private Element element;
    private SupportedTypesContainer typesContainer;

    public ComplexSearchRuleField(ComplexSearchRule searchRule,
                                  Element element,
                                  SupportedTypesContainer typesContainer) {
        this.searchRule = searchRule;
        this.element = element;
        this.typesContainer = typesContainer;
    }

    @Override
    public String getFieldClassName() {
        return typesContainer
            .getSupportedTypesMap().get(searchRule.getType()).getElementClass().getName();
    }

    @Override
    public JavaAnnotation getAnnotation() {
        Class fieldAnnotationClass = typesContainer.getSupportedTypesMap().get(searchRule.getType())
            .getElementAnnotation();
        return new ComplexSearchRuleAnnotation(searchRule, element, fieldAnnotationClass);
    }

    @Override
    public String getFieldName() {
        return firstLetterDown(splitCamelCase(searchRule.getRequiredValue(element)));
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
