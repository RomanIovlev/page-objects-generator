package com.epam.page.object.generator.adapter.searchRuleFields;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.epam.page.object.generator.adapter.searchRuleAnnotations.FormSearchRuleAnnotation;
import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.adapter.IJavaField;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import javax.lang.model.element.Modifier;

public class FormSearchRuleField implements IJavaField {

    private FormSearchRule searchRule;
    private SupportedTypesContainer typesContainer;

    public FormSearchRuleField(FormSearchRule searchRule,
                               SupportedTypesContainer typesContainer) {
        this.searchRule = searchRule;
        this.typesContainer = typesContainer;
    }

    @Override
    public String getFieldClassName() {
        return searchRule.getSection();
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        Class fieldAnnotationClass = typesContainer.getSupportedTypesMap().get(searchRule.getTypeName())
            .getElementAnnotation();
        return new FormSearchRuleAnnotation(searchRule, fieldAnnotationClass);
    }

    @Override
    public String getFieldName() {
        return firstLetterDown(splitCamelCase(searchRule.getSection()));
    }

    @Override
    public Modifier[] getModifiers() {
        return new Modifier[]{PUBLIC};
    }
}
