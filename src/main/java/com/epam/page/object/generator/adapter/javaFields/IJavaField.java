package com.epam.page.object.generator.adapter.javaFields;

import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import javax.lang.model.element.Modifier;

public interface IJavaField {

    String getFullFieldClass();

    String getFieldName();

    IJavaAnnotation getAnnotation();

    Modifier[] getModifiers();
}
