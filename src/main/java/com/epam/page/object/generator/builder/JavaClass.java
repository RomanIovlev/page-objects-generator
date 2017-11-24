package com.epam.page.object.generator.builder;

import java.io.IOException;
import java.util.List;
import javax.lang.model.element.Modifier;

public interface JavaClass {
    String getClassName() throws IOException;

    Class getSuperClass();

    JavaAnnotation getAnnotation();

    List<JavaField> getFieldsList() throws IOException;

    Modifier[] getModifiers();

}
