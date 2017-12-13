package com.epam.page.object.generator.adapter.javaclass;

import com.epam.page.object.generator.adapter.annotation.IJavaAnnotation;
import com.epam.page.object.generator.adapter.filed.IJavaField;
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
