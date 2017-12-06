package com.epam.page.object.generator.adapter.searchRuleAnnotations;

import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import java.util.Collections;
import java.util.List;

public class FormSearchRuleAnnotation implements IJavaAnnotation {

    private FormSearchRule searchRule;
    private Class fieldAnnotationClass;

    public FormSearchRuleAnnotation(FormSearchRule searchRule, Class fieldAnnotationClass) {
        this.searchRule = searchRule;
        this.fieldAnnotationClass = fieldAnnotationClass;
    }

    @Override
    public Class getAnnotationClass() {
        return fieldAnnotationClass;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers()
        throws XpathToCssTransformerException {

        return Collections.singletonList(new AnnotationMember(searchRule.getSelector().getType(), "$S",
            searchRule.getSelector().getValue()));
    }
}
