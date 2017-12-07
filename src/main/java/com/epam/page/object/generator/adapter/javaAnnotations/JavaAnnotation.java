package com.epam.page.object.generator.adapter.javaAnnotations;

import java.util.List;

public class JavaAnnotation implements IJavaAnnotation {

    private Class annotationClass;
    private List<AnnotationMember> annotationMembers;

    public JavaAnnotation(Class annotationClass, List<AnnotationMember> annotationMembers) {
        this.annotationClass = annotationClass;
        this.annotationMembers = annotationMembers;
    }

    @Override
    public Class getAnnotationClass() {
        return annotationClass;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers() {
        return annotationMembers;
    }
}
