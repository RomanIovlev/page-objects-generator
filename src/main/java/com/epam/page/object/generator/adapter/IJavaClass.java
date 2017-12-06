package com.epam.page.object.generator.adapter;

import java.util.List;
import javax.lang.model.element.Modifier;

public interface IJavaClass {

    String getPackageName();

    String getClassName();

    Class getSuperClass();

    IJavaAnnotation getAnnotation();

    List<IJavaField> getFieldsList();

    Modifier getModifier();

}
