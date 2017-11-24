package com.epam.page.object.generator.builder;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public abstract class JavaPoetClass implements JavaClass {

    private String outputDir;
    private String packageName;

    public JavaPoetClass(String outputDir, String packageName) {
        this.outputDir = outputDir;
        this.packageName = packageName;
    }

    public void writeClass()
        throws IOException {
        JavaFile.builder(packageName, buildTypeSpec())
            .build()
            .writeTo(Paths.get(outputDir));
    }

    private TypeSpec buildTypeSpec() throws IOException {
        List<FieldSpec> fieldSpecList = new ArrayList<>();

        for (JavaField field : getFieldsList()) {
            fieldSpecList.add(buildFieldSpec(field));
        }

        return TypeSpec.classBuilder(getClassName())
            .addModifiers(getModifiers())
            .superclass(getSuperClass())
            .addAnnotation(buildAnnotationSpec(getAnnotation()))
            .addFields(fieldSpecList)
            .build();
    }

    private FieldSpec buildFieldSpec(JavaField field) {
        return FieldSpec.builder(field.getFieldClassName(), field.getFieldName())
            .addModifiers(field.getModifiers())
            .addAnnotation(buildAnnotationSpec(field.getAnnotation()))
            .build();
    }

    private AnnotationSpec buildAnnotationSpec(JavaAnnotation annotation) {
        AnnotationSpec annotationSpec =
            AnnotationSpec
                .builder(annotation.getAnnotationClass())
                .build();

        for (AnnotationMember annotationMember : annotation.getAnnotationMembers()) {
            if (annotationMember.format.equals("$S")) {
                annotationSpec = annotationSpec.toBuilder()
                    .addMember(annotationMember.name, annotationMember.format, annotationMember.arg)
                    .build();
            } else if (annotationMember.format.equals("$L")) {
                annotationSpec = annotationSpec.toBuilder()
                    .addMember(annotationMember.name, annotationMember.format,
                        annotationMember.annotation)
                    .build();
            }
        }

        return annotationSpec;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getPackageName() {
        return packageName;
    }

    public abstract String getClassName() throws IOException;

    public abstract Class getSuperClass();

    public abstract JavaAnnotation getAnnotation();

    public abstract List<JavaField> getFieldsList();

    public abstract Modifier[] getModifiers();

    protected static class AnnotationMember {

        String name;
        String format;
        String arg;
        AnnotationSpec annotation;

        AnnotationMember(String name, String format, String arg) {
            this.name = name;
            this.format = format;
            this.arg = arg;
        }

        AnnotationMember(String name, String format, AnnotationSpec annotation) {
            this.name = name;
            this.format = format;
            this.annotation = annotation;
        }

    }
}
