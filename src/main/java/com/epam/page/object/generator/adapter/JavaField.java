package com.epam.page.object.generator.adapter;

import javax.lang.model.element.Modifier;

public class JavaField {

    private String fullClassField;
    private String fieldName;
    private JavaAnnotation annotation;
    private Modifier[] modifiers;

    public JavaField(String fullClassField, String fieldName, JavaAnnotation annotation,
                     Modifier[] modifiers) {
        this.fullClassField = fullClassField;
        this.fieldName = fieldName;
        this.annotation = annotation;
        this.modifiers = modifiers;
    }

    public String getFullFieldClass() {
        return fullClassField;
    }

    public JavaAnnotation getAnnotation() {
        return annotation;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Modifier[] getModifiers() {
        return modifiers;
    }
}