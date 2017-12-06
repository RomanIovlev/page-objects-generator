package com.epam.page.object.generator.adapter;

import javax.lang.model.element.Modifier;

public interface IJavaField {

    String getFieldClassName();

    String getFieldName();

    IJavaAnnotation getAnnotation();

    Modifier[] getModifiers();
}
