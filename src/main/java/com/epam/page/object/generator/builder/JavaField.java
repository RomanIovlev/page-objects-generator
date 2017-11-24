package com.epam.page.object.generator.builder;

import com.squareup.javapoet.ClassName;
import javax.lang.model.element.Modifier;

public interface JavaField {

    ClassName getFieldClassName();
    JavaAnnotation getAnnotation();
    String getFieldName();
    Modifier[] getModifiers();
}
