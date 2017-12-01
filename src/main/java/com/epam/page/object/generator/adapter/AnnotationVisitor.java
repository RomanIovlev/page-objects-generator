package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.adapter.JavaAnnotation.AnnotationMember;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import org.jsoup.nodes.Element;

public interface AnnotationVisitor {
    AnnotationMember getAnnotation(CommonSearchRule searchRule, Element element);
    AnnotationMember getAnnotation(ComplexSearchRule searchRule, Element element);
    AnnotationMember getAnnotation(FormSearchRule searchRule, Element element);
}
