package com.epam.page.object.generator.adapter;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaFileWriter {

    private final static Logger logger = LoggerFactory.getLogger(JavaFileWriter.class);

    public void writeFiles(String outputDir, List<JavaClass> javaClasses) throws IOException {
        for (JavaClass javaClass : javaClasses) {
            logger.debug("Start generating " + javaClass + "\n");
            writeClass(outputDir, javaClass);
            logger.debug("Generate " + javaClass + "\n");
        }
    }

    private void writeClass(String outputDir, JavaClass javaClass) throws IOException {
        JavaFile.builder(javaClass.getPackageName(), buildTypeSpec(javaClass))
            .build()
            .writeTo(Paths.get(outputDir));
    }

    private TypeSpec buildTypeSpec(JavaClass javaClass) {
        List<FieldSpec> fieldSpecList = new ArrayList<>();

        for (JavaField field : javaClass.getFieldsList()) {
            logger.debug("Start building " + field);
            fieldSpecList.add(buildFieldSpec(field));
            logger.debug("Finish building field\n");
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

    private FieldSpec buildFieldSpec(JavaField field) {
        return FieldSpec
            .builder(ClassName.bestGuess(field.getFullFieldClass()), field.getFieldName())
            .addModifiers(field.getModifiers())
            .addAnnotation(buildAnnotationSpec(field.getAnnotation()))
            .build();
    }

    private AnnotationSpec buildAnnotationSpec(JavaAnnotation annotation) {
        logger.debug("Start building " + annotation);
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
            logger.debug("Build " + annotationMember);
        }
        logger.debug("Finish building annotation");

        return annotationSpec;
    }
}
