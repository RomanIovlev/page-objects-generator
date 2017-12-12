package com.epam.page.object.generator.adapter.fileds;

import com.epam.page.object.generator.adapter.annotations.IJavaAnnotation;
import javax.lang.model.element.Modifier;

public interface IJavaField {

    String getFullFieldClass();

    String getFieldName();

    IJavaAnnotation getAnnotation();

    Modifier[] getModifiers();
}
