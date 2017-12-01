package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.adapter.JavaAnnotation.AnnotationMember;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import org.jsoup.nodes.Element;

public interface Annotatable {
    AnnotationMember getAnnotation(SearchRule searchRule, Element element);
}
