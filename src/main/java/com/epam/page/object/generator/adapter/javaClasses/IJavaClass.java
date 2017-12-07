package com.epam.page.object.generator.adapter.javaClasses;

import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.IJavaField;
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
