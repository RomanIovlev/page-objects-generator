package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.adapter.annotation.AnnotationMember;
import com.epam.page.object.generator.adapter.annotation.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaclass.IJavaClass;
import com.epam.page.object.generator.adapter.filed.IJavaField;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JavaFileWriter {

    public void writeFiles(String outputDir, List<IJavaClass> javaClasses) throws IOException {
        for (IJavaClass javaClass : javaClasses) {
            writeClass(outputDir, javaClass);
        }
    }

    private void writeClass(String outputDir, IJavaClass javaClass) throws IOException {
        JavaFile.builder(javaClass.getPackageName(), buildTypeSpec(javaClass))
            .build()
            .writeTo(Paths.get(outputDir));
    }

    private TypeSpec buildTypeSpec(IJavaClass javaClass) {
        List<FieldSpec> fieldSpecList = new ArrayList<>();

        for (IJavaField field : javaClass.getFieldsList()) {
            fieldSpecList.add(buildFieldSpec(field));
        }

        TypeSpec.Builder builder = TypeSpec.classBuilder(javaClass.getClassName())
            .addModifiers(javaClass.getModifier())
            .superclass(javaClass.getSuperClass())
            .addFields(fieldSpecList);

        if (javaClass.getAnnotation() != null) {
            builder.addAnnotation(buildAnnotationSpec(javaClass.getAnnotation()));
        }

        return builder.build();
    }

    private FieldSpec buildFieldSpec(IJavaField field) {
        return FieldSpec
            .builder(ClassName.bestGuess(field.getFullFieldClass()), field.getFieldName())
            .addModifiers(field.getModifiers())
            .addAnnotation(buildAnnotationSpec(field.getAnnotation()))
            .build();
    }

    private AnnotationSpec buildAnnotationSpec(IJavaAnnotation annotation) {
        AnnotationSpec annotationSpec =
            AnnotationSpec
                .builder(annotation.getAnnotationClass())
                .build();

        for (AnnotationMember annotationMember : annotation.getAnnotationMembers()) {
            if (annotationMember.getFormat().equals("$S")) {
                annotationSpec = annotationSpec.toBuilder()
                    .addMember(annotationMember.getName(), annotationMember.getFormat(),
                        annotationMember.getArg())
                    .build();
            } else if (annotationMember.getFormat().equals("$L")) {
                annotationSpec = annotationSpec.toBuilder()
                    .addMember(annotationMember.getName(), annotationMember.getFormat(),
                        buildAnnotationSpec(annotationMember.getAnnotation()))
                    .build();
            }
        }

        return annotationSpec;
    }
}
