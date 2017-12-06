package com.epam.page.object.generator.adapter.searchRuleFields;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.adapter.IJavaField;
import com.epam.page.object.generator.adapter.searchRuleAnnotations.FormInnerSearchRuleAnnotation;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;

public class FormInnerSearchRuleField implements IJavaField {

    private FormInnerSearchRule searchRule;
    private Element element;
    private SupportedTypesContainer typesContainer;

    public FormInnerSearchRuleField(FormInnerSearchRule searchRule,
                                    Element element,
                                    SupportedTypesContainer typesContainer) {
        this.searchRule = searchRule;
        this.element = element;
        this.typesContainer = typesContainer;
    }

    @Override
    public String getFieldClassName() {
        return typesContainer
            .getSupportedTypesMap().get(searchRule.getTypeName()).getElementClass().getName();
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        Class fieldAnnotationClass = typesContainer.getSupportedTypesMap()
            .get(searchRule.getTypeName())
            .getElementAnnotation();
        return new FormInnerSearchRuleAnnotation(searchRule, element, fieldAnnotationClass);
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
