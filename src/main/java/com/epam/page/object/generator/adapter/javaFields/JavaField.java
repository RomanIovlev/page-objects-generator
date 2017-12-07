package com.epam.page.object.generator.adapter.javaFields;

import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import javax.lang.model.element.Modifier;

public class JavaField implements IJavaField {

    private String fullClassField;
    private String fieldName;
    private IJavaAnnotation annotation;
    private Modifier[] modifiers;

    public JavaField(String fullClassField, String fieldName, IJavaAnnotation annotation,
                     Modifier[] modifiers) {
        this.fullClassField = fullClassField;
        this.fieldName = fieldName;
        this.annotation = annotation;
        this.modifiers = modifiers;
    }

    @Override
    public String getFullFieldClass() {
        return fullClassField;
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
