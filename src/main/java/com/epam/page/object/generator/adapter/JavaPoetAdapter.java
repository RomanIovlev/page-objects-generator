package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static com.epam.page.object.generator.utils.URLUtils.getDomainName;
import static com.epam.page.object.generator.utils.URLUtils.getPageTitle;
import static com.epam.page.object.generator.utils.URLUtils.getUrlWithoutDomain;

import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.epam.page.object.generator.writer.JavaFileWriter;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import javax.lang.model.element.Modifier;
import org.openqa.selenium.support.FindBy;

public class JavaPoetAdapter implements JavaFileWriter {

    private SupportedTypesContainer supportedTypesContainer;

    public JavaPoetAdapter(SupportedTypesContainer supportedTypesContainer) {
        this.supportedTypesContainer = supportedTypesContainer;
    }

    private class AnnotationMember {

        String name;
        String format;
        String arg;

        AnnotationMember(String name, String format, String arg) {
            this.name = name;
            this.format = format;
            this.arg = arg;
        }

    }

    private TypeSpec buildPageClass(List<SearchRule> searchRules, String url) throws IOException {
        List<FieldSpec> fields = new ArrayList<>();
        String pageClassName = firstLetterUp(splitCamelCase(getPageTitle(url)));

        for (SearchRule searchRule : searchRules) {
            ClassAndAnnotationPair currentElementPair = supportedTypesContainer
                .getSupportedTypesMap().get(searchRule.getType().toLowerCase());
            Class fieldClass = currentElementPair.getElementClass();
            Class fieldAnnotationClass = currentElementPair.getElementAnnotation();

            String elementRequiredValue = searchRule.getRequiredValueFromFoundElement(url).get(0);

            AnnotationSpec elementFieldAnnotation;

            // добавить аннотацию
            if (searchRule.getInnerSearchRules() == null) {
                // простая аннотация
                AnnotationMember commonElementAnnotationMember;

                if (!searchRule.getRequiredAttribute().equalsIgnoreCase("text")) {
                    if (searchRule.getCss() == null) {
                        searchRule = XpathToCssTransformer.transformRule(searchRule);
                    }

                    commonElementAnnotationMember = new AnnotationMember("css", "$S",
                        resultCssSelector(searchRule, elementRequiredValue));
                } else {
                    commonElementAnnotationMember = new AnnotationMember("css", "$S",
                        resultXpathSelector(searchRule, elementRequiredValue));
                }

                elementFieldAnnotation = buildAnnotationSpec(fieldAnnotationClass,
                    commonElementAnnotationMember);
            } else {
                // сложная аннотация

                // TODO: переписать с нащшими внутренними методами

                elementRequiredValue = searchRule.getType();

                AnnotationSpec.Builder annotationBuilder = AnnotationSpec
                    .builder(fieldAnnotationClass);
                String searchAttribute = searchRule.getRequiredAttribute();

                for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
                    AnnotationSpec.Builder innerAnnotation = AnnotationSpec.builder(FindBy.class);
                    String annotationElementName = innerSearchRule.getTitle();

                    if (innerSearchRule.getCss() != null) {
                        innerAnnotation.addMember("css", "$S", resultCssSelector(innerSearchRule,
                            innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
                    } else {
                        innerAnnotation
                            .addMember("xpath", "$S", resultXpathSelector(innerSearchRule,
                                innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
                    }

                    annotationBuilder
                        .addMember(annotationElementName, "$L", innerAnnotation.build());
                }
                // Конец TODO

                elementFieldAnnotation = annotationBuilder.build();
            }

            fields.add(buildFieldSpec(fieldClass, elementFieldAnnotation,
                firstLetterDown(splitCamelCase(elementRequiredValue)), PUBLIC));
        }

        return buildTypeSpec(pageClassName, WebPage.class, fields, PUBLIC);
    }

    private TypeSpec buildSiteClass(String packageName, List<String> urls)
        throws IOException, URISyntaxException {
        List<FieldSpec> pageFields = new ArrayList<>();

        for (String url : urls) {
            String titleName = splitCamelCase(getPageTitle(url));
            String pageFieldName = firstLetterDown(titleName);
            String pageClassName = firstLetterUp(titleName);
            ClassName pageClass = getPageClassName(packageName, pageClassName);

            AnnotationSpec pageFieldAnnotation = buildAnnotationSpec(JPage.class,
                new AnnotationMember("url", "$S", getUrlWithoutDomain(url)),
                new AnnotationMember("title", "$S", getPageTitle(url)));

            pageFields
                .add(buildFieldSpec(pageClass, pageFieldAnnotation, pageFieldName, PUBLIC, STATIC));
        }

        AnnotationMember siteAnnotationMember = new AnnotationMember("domain", "$S",
            getDomainName(urls));
        AnnotationSpec siteClassAnnotation = buildAnnotationSpec(JSite.class, siteAnnotationMember);

        return buildTypeSpec("Site", WebSite.class, siteClassAnnotation, pageFields, PUBLIC);
    }

    private ClassName getPageClassName(String packageName, String pageClassName) {
        return ClassName.get(packageName + ".page", pageClassName);
    }

    private TypeSpec buildTypeSpec(String className, Class superClass,
                                   AnnotationSpec annotationSpec,
                                   List<FieldSpec> fieldSpecs, Modifier... modifiers) {
        return TypeSpec.classBuilder(className)
            .addModifiers(modifiers)
            .superclass(superClass)
            .addAnnotation(annotationSpec)
            .addFields(fieldSpecs)
            .build();
    }

    private TypeSpec buildTypeSpec(String className, Class superClass,
                                   List<FieldSpec> fieldSpecs, Modifier... modifiers) {
        return TypeSpec.classBuilder(className)
            .addModifiers(modifiers)
            .superclass(superClass)
            .addFields(fieldSpecs)
            .build();
    }

    private FieldSpec buildFieldSpec(ClassName fieldClass, AnnotationSpec annotationSpec,
                                     String fieldName, Modifier... modifiers) {
        return FieldSpec.builder(fieldClass, fieldName)
            .addModifiers(modifiers)
            .addAnnotation(annotationSpec)
            .build();
    }

    private FieldSpec buildFieldSpec(Class fieldClass, AnnotationSpec annotationSpec,
                                     String fieldName, Modifier... modifiers) {
        return FieldSpec.builder(fieldClass, fieldName)
            .addModifiers(modifiers)
            .addAnnotation(annotationSpec)
            .build();
    }

    private AnnotationSpec buildAnnotationSpec(Class annotationClass,
                                               AnnotationMember... annotationMembers) {
        AnnotationSpec annotationSpec = AnnotationSpec.builder(annotationClass).build();

        for (AnnotationMember annotationMember : annotationMembers) {
            annotationSpec = annotationSpec.toBuilder()
                .addMember(annotationMember.name, annotationMember.format, annotationMember.arg)
                .build();
        }

        return annotationSpec;
    }

    @Override
    public void writeFile(String packageName, String outputDir, List<SearchRule> searchRules,
                          List<String> urls)
        throws IOException, URISyntaxException {
        JavaFile javaFile;

        // Для сайта
        String sitePackageName = packageName + ".site";

        TypeSpec siteClass = buildSiteClass(sitePackageName, urls);

        javaFile = JavaFile.builder(sitePackageName, siteClass)
            .build();

        javaFile.writeTo(Paths.get(outputDir));

        // для страницы
        String pagesPackageName = packageName + ".page";

        for (String url : urls) {
            TypeSpec pageClass = buildPageClass(searchRules, url);

            javaFile = JavaFile.builder(pagesPackageName, pageClass)
                .build();

            javaFile.writeTo(Paths.get(outputDir));
        }
    }

}