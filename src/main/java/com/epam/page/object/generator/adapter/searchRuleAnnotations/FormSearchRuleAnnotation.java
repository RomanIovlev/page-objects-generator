package com.epam.page.object.generator.adapter.searchRuleAnnotations;

import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FormSearchRuleAnnotation implements JavaAnnotation {

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
