package com.epam.page.object.generator.adapter;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import javax.lang.model.element.Modifier;

public class JavaPoet {

    public static void writeClass(String outputDir, String packageName, TypeSpec typeSpec)
        throws IOException {
        JavaFile.builder(packageName, typeSpec)
            .build()
            .writeTo(Paths.get(outputDir));
    }

    public static TypeSpec buildTypeSpec(String className,
                                  Class superClass,
                                  AnnotationSpec annotationSpec,
                                  List<FieldSpec> fieldSpecs,
                                  Modifier... modifiers) {
        return TypeSpec.classBuilder(className)
            .addModifiers(modifiers)
            .superclass(superClass)
            .addAnnotation(annotationSpec)
            .addFields(fieldSpecs)
            .build();
    }

    public static TypeSpec buildTypeSpec(String className,
                                  Class superClass,
                                  List<FieldSpec> fieldSpecs,
                                  Modifier... modifiers) {
        return TypeSpec.classBuilder(className)
            .addModifiers(modifiers)
            .superclass(superClass)
            .addFields(fieldSpecs)
            .build();
    }

    public static FieldSpec buildFieldSpec(ClassName fieldClass,
                                    AnnotationSpec annotationSpec,
                                    String fieldName,
                                    Modifier... modifiers) {
        return FieldSpec.builder(fieldClass, fieldName)
            .addModifiers(modifiers)
            .addAnnotation(annotationSpec)
            .build();
    }

    public static FieldSpec buildFieldSpec(Class fieldClass,
                                    AnnotationSpec annotationSpec,
                                    String fieldName,
                                    Modifier... modifiers) {
        return FieldSpec.builder(fieldClass, fieldName)
            .addModifiers(modifiers)
            .addAnnotation(annotationSpec)
            .build();
    }

}
