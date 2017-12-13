package com.epam.page.object.generator.adapter.filed;

import com.epam.page.object.generator.adapter.annotation.IJavaAnnotation;
import javax.lang.model.element.Modifier;

public interface IJavaField {

    String getFullFieldClass();

    String getFieldName();

    IJavaAnnotation getAnnotation();

    Modifier[] getModifiers();
}
