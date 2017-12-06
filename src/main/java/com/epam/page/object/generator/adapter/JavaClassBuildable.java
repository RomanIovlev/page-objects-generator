package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.builders.JavaClassBuilder;
import java.util.List;

public interface JavaClassBuildable {

    IJavaClass accept(JavaClassBuilder javaClassBuilder);

    List<IJavaField> getFields();

    IJavaAnnotation getAnnotation();
}
