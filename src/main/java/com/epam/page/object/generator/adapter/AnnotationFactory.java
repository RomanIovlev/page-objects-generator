package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.adapter.JavaAnnotation.AnnotationMember;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import org.jsoup.nodes.Element;

public class AnnotationFactory implements AnnotationVisitor {

    @Override
    public AnnotationMember getAnnotation(CommonSearchRule searchRule, Element element) {
        return null;
    }

    @Override
    public AnnotationMember getAnnotation(ComplexSearchRule searchRule, Element element) {
        return null;
    }

    @Override
    public AnnotationMember getAnnotation(FormSearchRule searchRule, Element element) {
        return null;
    }
}
