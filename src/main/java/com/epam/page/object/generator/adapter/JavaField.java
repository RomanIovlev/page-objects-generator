package com.epam.page.object.generator.adapter;

import javax.lang.model.element.Modifier;
import java.util.Arrays;


/**
 * JavaField is struct class that contains necessary attributes for field of {@link JavaClass}
 */
public class JavaField {

    private String fullClassField;
    private String fieldName;
    private JavaAnnotation annotation;
    private Modifier[] modifiers;


    /**
     * @param fullClassField is reference{@link JavaClass} type.
     * @param fieldName      is a name of field.
     * @param annotation     is a field {@link JavaAnnotation} annotation.
     * @param modifiers      List of all modifiers of field.
     */
    public JavaField(String fullClassField, String fieldName, JavaAnnotation annotation,
                     Modifier[] modifiers) {
        this.fullClassField = fullClassField;
        this.fieldName = fieldName;
        this.annotation = annotation;
        this.modifiers = modifiers;
    }

    /**
     * @return {@link JavaClass} field.
     */
    public String getFullFieldClass() {
        return fullClassField;
    }

    /**
     * @return {@link JavaAnnotation} of field.
     */
    public JavaAnnotation getAnnotation() {
        return annotation;
    }

    /**
     * @return Name of field.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     *
     * @return List of all modifiers of field.
     */
    public Modifier[] getModifiers() {
        return modifiers;
    }

    @Override
    public String toString() {
        return "JavaField{" +
                "fullClassField='" + fullClassField + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", annotation=" + annotation +
                ", modifiers=" + Arrays.toString(modifiers) +
                '}';
    }
}
