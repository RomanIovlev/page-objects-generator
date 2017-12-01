package com.epam.page.object.generator.adapter;

import javax.lang.model.element.Modifier;

public interface JavaField {

    String getFieldClassName();

    JavaAnnotation getAnnotation();

    String getFieldName();

    Modifier[] getModifiers();
}
