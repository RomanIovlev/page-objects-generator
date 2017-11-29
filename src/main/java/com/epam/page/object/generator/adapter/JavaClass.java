package com.epam.page.object.generator.adapter;

import java.util.List;
import javax.lang.model.element.Modifier;

public interface JavaClass {

    String getPackageName();

    String getClassName();

    Class getSuperClass();

    JavaAnnotation getAnnotation();

    List<JavaField> getFieldsList();

    Modifier[] getModifiers();

}
