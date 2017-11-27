package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FormOrSectionAnnotation implements JavaAnnotation {

    private SearchRule searchRule;
    private Class fieldAnnotationClass;

    public FormOrSectionAnnotation(SearchRule searchRule, Class fieldAnnotationClass) {
        this.searchRule = searchRule;
        this.fieldAnnotationClass = fieldAnnotationClass;
    }

    @Override
    public Class getAnnotationClass() {
        return fieldAnnotationClass;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers()
        throws IOException, XpathToCssTransformerException {
        AnnotationMember annotationMember;
        if (searchRule.getCss() != null) {
            annotationMember = new AnnotationMember("css", "$S", searchRule.getCss());
        } else {
            annotationMember =
                new AnnotationMember("xpath", "$S", searchRule.getXpath());
        }

        return Collections.singletonList(annotationMember);
    }
}
