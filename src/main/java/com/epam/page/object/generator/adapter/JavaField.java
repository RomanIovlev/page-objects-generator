package com.epam.page.object.generator.adapter;

import javax.lang.model.element.Modifier;

public class JavaField implements IJavaField {

    private String fieldClass;
    private String fieldName;
    private IJavaAnnotation annotation;
    private Modifier[] modifiers;

    public JavaField(String fieldClass, String fieldName,
                     IJavaAnnotation annotation, Modifier[] modifiers) {
        this.fieldClass = fieldClass;
        this.fieldName = fieldName;
        this.annotation = annotation;
        this.modifiers = modifiers;
    }

    @Override
    public String getFieldClassName() {
        return fieldClass;
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        return annotation;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Modifier[] getModifiers() {
        return modifiers;
    }
}
