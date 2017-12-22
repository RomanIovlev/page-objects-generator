package com.epam.page.object.generator.adapter.classbuildable;

import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import java.util.List;

public interface JavaClassBuildable {

    JavaClass accept(JavaClassBuilder javaClassBuilder);

    List<JavaField> buildFields(String packageName);

    JavaAnnotation buildAnnotation();
}
