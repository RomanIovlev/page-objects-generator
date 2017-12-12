package com.epam.page.object.generator.adapter.classbuildable;

import com.epam.page.object.generator.adapter.annotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.fileds.IJavaField;
import com.epam.page.object.generator.adapter.classes.IJavaClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import java.util.List;

public interface JavaClassBuildable {

    IJavaClass accept(JavaClassBuilder javaClassBuilder);

    List<IJavaField> buildFields(String packageName);

    IJavaAnnotation buildAnnotation();
}
