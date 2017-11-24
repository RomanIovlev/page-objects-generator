package com.epam.page.object.generator.builder;

import java.util.List;
import javax.lang.model.element.Modifier;

public interface JavaClass {
    String getClassName();

    Class getSuperClass();

    JavaAnnotation getAnnotation();

    List<JavaField> getFieldsList();

    Modifier[] getModifiers();

}
