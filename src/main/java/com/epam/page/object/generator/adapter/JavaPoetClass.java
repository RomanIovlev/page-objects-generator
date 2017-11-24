package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
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
        throws IOException, XpathToCssTransformerException {
        JavaFile.builder(packageName, buildTypeSpec())
            .build()
            .writeTo(Paths.get(outputDir));
    }

    private TypeSpec buildTypeSpec() throws IOException, XpathToCssTransformerException {
        List<FieldSpec> fieldSpecList = new ArrayList<>();

        for (JavaField field : getFieldsList()) {
            fieldSpecList.add(buildFieldSpec(field));
        }

        TypeSpec.Builder builder =  TypeSpec.classBuilder(getClassName())
            .addModifiers(getModifiers())
            .superclass(getSuperClass())
            .addFields(fieldSpecList);

        if (getAnnotation() != null) builder.addAnnotation(buildAnnotationSpec(getAnnotation()));

        return builder.build();
    }

    private FieldSpec buildFieldSpec(JavaField field)
        throws IOException, XpathToCssTransformerException {
        FieldSpec.Builder builder =
            field.getFieldClassName() == null
                ? FieldSpec.builder(field.getFieldClass(), field.getFieldName())
                : FieldSpec.builder(field.getFieldClassName(), field.getFieldName());
        return builder
            .addModifiers(field.getModifiers())
            .addAnnotation(buildAnnotationSpec(field.getAnnotation()))
            .build();
    }

    private AnnotationSpec buildAnnotationSpec(JavaAnnotation annotation)
        throws IOException, XpathToCssTransformerException {
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

    public abstract List<JavaField> getFieldsList() throws IOException;

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
