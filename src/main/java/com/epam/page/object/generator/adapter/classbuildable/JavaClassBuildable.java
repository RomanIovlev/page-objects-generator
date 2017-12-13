package com.epam.page.object.generator.adapter.classbuildable;

import com.epam.page.object.generator.adapter.annotation.IJavaAnnotation;
import com.epam.page.object.generator.adapter.filed.IJavaField;
import com.epam.page.object.generator.adapter.javaclass.IJavaClass;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import java.util.List;

public interface JavaClassBuildable {

    IJavaClass accept(JavaClassBuilder javaClassBuilder);

    List<IJavaField> buildFields(String packageName);

    IJavaAnnotation buildAnnotation();
}
