package com.epam.page.object.generator.adapter.classes;

import com.epam.page.object.generator.adapter.annotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.fileds.IJavaField;
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
