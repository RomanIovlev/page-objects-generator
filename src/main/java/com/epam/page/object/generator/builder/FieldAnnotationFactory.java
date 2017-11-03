package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.AnnotationSpec;
import java.io.IOException;

public class FieldAnnotationFactory {

    private Class annotationClass;

    private AnnotationsBuilder annotationsBuilder = new AnnotationsBuilder();

    public FieldAnnotationFactory(Class annotationClass) {
        this.annotationClass = annotationClass;
    }

    public AnnotationSpec buildAnnotation(SearchRule searchRule, String elementsRequiredValue, String url) throws IOException {
        if (searchRule.getInnerSearchRules() == null) {
            return annotationsBuilder.buildCommonAnnotation(searchRule, elementsRequiredValue, annotationClass);
        } else {
            return annotationsBuilder.buildComplexAnnotation(searchRule, url, annotationClass);
        }
    }
}