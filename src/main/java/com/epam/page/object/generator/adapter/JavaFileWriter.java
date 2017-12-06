package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.adapter.IJavaAnnotation.AnnotationMember;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.webElementGroups.WebElementGroup;
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

    private SupportedTypesContainer typesContainer;

    public JavaFileWriter(SupportedTypesContainer typesContainer) {
        this.typesContainer = typesContainer;
    }

    public void writeFiles(String outputDir, String packageName, List<WebPage> webPages)
        throws XpathToCssTransformerException, IOException {

        writeClass(outputDir, new SiteClass(packageName + ".site", webPages));

        for (WebPage webPage : webPages) {
            writeClass(outputDir, new PageClass(packageName + ".page",
                webPage,
                typesContainer));

            for (WebElementGroup webElementGroup : webPage.getWebElementGroups()) {
                SearchRule searchRule = webElementGroup.getSearchRule();
                if (searchRule instanceof FormSearchRule) {
                    writeClass(outputDir,
                        new FormClass(packageName + ".form",
                            webPage,
                            (FormSearchRule) searchRule,
                            typesContainer));
                }
            }
        }
    }

    private void writeClass(String outputDir, IJavaClass javaClass)
        throws IOException, XpathToCssTransformerException {
        JavaFile.builder(javaClass.getPackageName(), buildTypeSpec(javaClass))
            .build()
            .writeTo(Paths.get(outputDir));
    }

    private TypeSpec buildTypeSpec(IJavaClass javaClass)
        throws IOException, XpathToCssTransformerException {
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

    private FieldSpec buildFieldSpec(IJavaField field)
        throws IOException, XpathToCssTransformerException {
        return FieldSpec
            .builder(ClassName.bestGuess(field.getFieldClassName()), field.getFieldName())
            .addModifiers(field.getModifiers())
            .addAnnotation(buildAnnotationSpec(field.getAnnotation()))
            .build();
    }

    private AnnotationSpec buildAnnotationSpec(IJavaAnnotation annotation)
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
                        buildAnnotationSpec(annotationMember.annotation))
                    .build();
            }
        }

        return annotationSpec;
    }

    public void writeFiles(String outputDir, List<IJavaClass> javaClasses)
        throws IOException, XpathToCssTransformerException {
        for (IJavaClass javaClass : javaClasses) {
            writeClass(outputDir, javaClass);
        }
    }
}
