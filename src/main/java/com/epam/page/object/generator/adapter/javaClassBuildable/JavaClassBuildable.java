package com.epam.page.object.generator.adapter.javaClassBuildable;

import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.IJavaField;
import com.epam.page.object.generator.adapter.javaClasses.IJavaClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import java.util.List;

public interface JavaClassBuildable {

    IJavaClass accept(JavaClassBuilder javaClassBuilder);

    List<IJavaField> getFields();

    IJavaAnnotation getAnnotation();
}
