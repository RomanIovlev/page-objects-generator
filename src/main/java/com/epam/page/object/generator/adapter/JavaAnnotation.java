package com.epam.page.object.generator.adapter;

import java.util.List;

/**
 * JavaAnnotation is a struct class that contains all necessary attributes for describing
 * annotations which {@link JavaClass} or {@link JavaField} has.
 */
public class JavaAnnotation {

    private Class<?> annotationClass;
    private List<AnnotationMember> annotationMembers;

    public JavaAnnotation(Class<?> annotationClass, List<AnnotationMember> annotationMembers) {
        this.annotationClass = annotationClass;
        this.annotationMembers = annotationMembers;
    }

    public Class<?> getAnnotationClass() {
        return annotationClass;
    }

    public List<AnnotationMember> getAnnotationMembers() {
        return annotationMembers;
    }

    @Override
    public String toString() {
        return "JavaAnnotation{" +
            "annotationClass=" + annotationClass +
            ", annotationMembers=" + annotationMembers +
            '}';
    }
}