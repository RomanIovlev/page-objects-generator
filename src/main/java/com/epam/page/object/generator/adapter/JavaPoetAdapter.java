package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterDown;
import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static com.epam.page.object.generator.utils.URLUtils.getDomainName;
import static com.epam.page.object.generator.utils.URLUtils.getPageTitle;
import static com.epam.page.object.generator.utils.URLUtils.getUrlWithoutDomain;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.Section;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
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
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.support.FindBy;

public class JavaPoetAdapter implements JavaFileWriter {

    private SupportedTypesContainer supportedTypesContainer;

    private XpathToCssTransformation xpathToCssTransformation;

    public JavaPoetAdapter(SupportedTypesContainer supportedTypesContainer,
                           XpathToCssTransformation xpathToCssTransformation) {
        this.supportedTypesContainer = supportedTypesContainer;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    private TypeSpec buildPageClass(List<SearchRule> searchRules, String url)
        throws IOException, XpathToCssTransformerException {
        String pageClassName = firstLetterUp(splitCamelCase(getPageTitle(url)));

        List<FieldSpec> fields = new ArrayList<>();

        for (SearchRule searchRule : searchRules) {
            Elements elements = searchRule.extractElementsFromWebSite(url);
            if (((elements != null)) && (
                elements.size() == 1)) {
                fields.add(createField(searchRule, elements.first()));
            }
        }
        return buildTypeSpec(pageClassName, WebPage.class, fields, PUBLIC);
    }


    private TypeSpec buildFormClass(SearchRule searchRule, String url)
        throws IOException, XpathToCssTransformerException {
        Class superClass;

        List<FieldSpec> fields = new ArrayList<>();

        Elements parentElements = searchRule.extractElementsFromWebSite(url);

        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
            Elements elements = innerSearchRule.extractElementsFromElement(parentElements.first());
            for (Element element : elements) {
                if (!innerSearchRule.getRequiredValueFromFoundElement(element).isEmpty()) {
                    fields.add(createField(innerSearchRule, element));
                }
            }
        }

        superClass =
            searchRule.getType().equals(SearchRuleType.FORM.getName()) ? Form.class : Section.class;

        return buildTypeSpec(searchRule.getSection(), superClass, fields, PUBLIC);
    }

    private AnnotationMember getAnnotationMemberFromRule(SearchRule searchRule, Element element)
        throws XpathToCssTransformerException, IOException {
        AnnotationMember annotationMember;
        String elementRequiredValue = searchRule.getRequiredValueFromFoundElement(element);
        if (!searchRule.getUniqueness().equalsIgnoreCase("text")) {
            if (searchRule.getCss() == null) {
                xpathToCssTransformation.transformRule(searchRule);
            }

            annotationMember = new AnnotationMember("css", "$S",
                resultCssSelector(searchRule, elementRequiredValue));
        } else {
            annotationMember = new AnnotationMember("xpath", "$S",
                resultXpathSelector(searchRule, elementRequiredValue));
        }
        return annotationMember;
    }

    private TypeSpec buildSiteClass(String packageName, List<String> urls)
        throws IOException, URISyntaxException {
        List<FieldSpec> pageFields = new ArrayList<>();

        for (String url : urls) {
            createPageFields(packageName, pageFields, url);
        }

        AnnotationMember siteAnnotationMember = new AnnotationMember("value", "$S",
            getDomainName(urls));
        AnnotationSpec siteClassAnnotation = buildAnnotationSpec(JSite.class,
            Collections.singletonList(siteAnnotationMember));

        return buildTypeSpec("Site", WebSite.class, siteClassAnnotation, pageFields, PUBLIC);
    }

    private void createPageFields(String packageName, List<FieldSpec> pageFields, String url)
        throws IOException, URISyntaxException {
        String titleName = splitCamelCase(getPageTitle(url));
        String pageFieldName = firstLetterDown(titleName);
        String pageClassName = firstLetterUp(titleName);
        ClassName pageClass = getPageClassName(packageName, pageClassName);
        List<AnnotationMember> pageAnnotations = new ArrayList<>();
        pageAnnotations.add(new AnnotationMember("url", "$S", getUrlWithoutDomain(url)));
        pageAnnotations.add(new AnnotationMember("title", "$S", getPageTitle(url)));

        AnnotationSpec pageFieldAnnotation = buildAnnotationSpec(JPage.class, pageAnnotations);

        pageFields
            .add(buildFieldSpec(pageClass, pageFieldAnnotation, pageFieldName, PUBLIC, STATIC));
    }

    private ClassName getPageClassName(String packageName, String pageClassName) {
        return ClassName
            .get(packageName.substring(0, packageName.length() - 5) + ".page", pageClassName);
    }

    private AnnotationSpec createCommonAnnotation(SearchRule searchRule, Element element,
                                                  Class fieldAnnotationClass)
        throws IOException, XpathToCssTransformerException {
        String requiredValue = searchRule.getRequiredValueFromFoundElement(element);
        if ((requiredValue != null) && (!requiredValue.isEmpty())) {
            AnnotationMember commonElementAnnotationMember = getAnnotationMemberFromRule(
                searchRule, element);
            return buildAnnotationSpec(fieldAnnotationClass,
                Collections.singletonList(commonElementAnnotationMember));
        } else {
            return null;
        }
    }

    private AnnotationSpec createComplexAnnotation(SearchRule searchRule, Element element,
                                                   Class fieldAnnotationClass)
        throws IOException, XpathToCssTransformerException {
        List<AnnotationMember> innerAnnotations = new ArrayList<>();

        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {

            String requiredValue = innerSearchRule.getRequiredValueFromFoundElement(element);
            String annotationElementName = innerSearchRule.getTitle();
            if ((requiredValue != null) && (!requiredValue.isEmpty())) {
                AnnotationMember innerAnnotationMember = getAnnotationMemberFromRule(
                    innerSearchRule,
                    element);

                AnnotationSpec innerAnnotation = buildAnnotationSpec(FindBy.class,
                    Collections.singletonList(innerAnnotationMember));
                innerAnnotations
                    .add(new AnnotationMember(annotationElementName, "$L", innerAnnotation));
            }
        }

        return buildAnnotationSpec(fieldAnnotationClass,
            innerAnnotations);
    }

    private AnnotationSpec createFormOrSectionAnnotation(SearchRule searchRule,
                                                         Class fieldAnnotationClass) {
        AnnotationMember annotationMember;
        if (searchRule.getCss() != null) {
            annotationMember = new AnnotationMember("css", "$S", searchRule.getCss());
        } else {
            annotationMember =
                new AnnotationMember("xpath", "$S", searchRule.getXpath());
        }

        return buildAnnotationSpec(fieldAnnotationClass,
            Collections.singletonList(annotationMember));
    }

    private FieldSpec createField(SearchRule searchRule, Element element)
        throws IOException, XpathToCssTransformerException {
        ClassAndAnnotationPair currentElementPair = supportedTypesContainer
            .getSupportedTypesMap().get(searchRule.getType());
        Class fieldClass = currentElementPair.getElementClass();
        Class fieldAnnotationClass = currentElementPair.getElementAnnotation();

        String elementRequiredValue;
        AnnotationSpec elementFieldAnnotation;

        if (SearchRuleTypeGroups.isCommonType(searchRule)) {
            elementRequiredValue = searchRule.getRequiredValueFromFoundElement(element);
            elementFieldAnnotation = createCommonAnnotation(searchRule, element,
                fieldAnnotationClass);
        } else if (SearchRuleTypeGroups.isComplexType(searchRule)) {
            elementRequiredValue = searchRule.getType();
            elementFieldAnnotation = createComplexAnnotation(searchRule, element,
                fieldAnnotationClass);
        } else if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
            elementRequiredValue = searchRule.getSection();
            elementFieldAnnotation = createFormOrSectionAnnotation(searchRule,
                fieldAnnotationClass);
        } else {
            //This type of search rule does not supported
            throw new UnsupportedOperationException(searchRule.getType()
                + " search rule type does not supported");
        }

        return buildFieldSpec(fieldClass, elementFieldAnnotation,
            firstLetterDown(splitCamelCase(elementRequiredValue)), PUBLIC);
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
                                               List<AnnotationMember> annotationMembers) {
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
        throws IOException, URISyntaxException, XpathToCssTransformerException {
        JavaFile javaFile;

        String sitePackageName = packageName + ".site";

        TypeSpec siteClass = buildSiteClass(sitePackageName, urls);

        javaFile = JavaFile.builder(sitePackageName, siteClass)
            .build();

        javaFile.writeTo(Paths.get(outputDir));

        String pagesPackageName = packageName + ".page";

        for (String url : urls) {
            TypeSpec pageClass = buildPageClass(searchRules, url);

            for (SearchRule searchRule : searchRules) {
                if (SearchRuleTypeGroups.isFormOrSectionType(searchRule)) {
                    JavaFile.builder(packageName + ".form",
                        buildFormClass(searchRule, url))
                        .build()
                        .writeTo(Paths.get(outputDir));
                }
            }

            javaFile = JavaFile.builder(pagesPackageName, pageClass)
                .build();

            javaFile.writeTo(Paths.get(outputDir));
        }
    }

    private class AnnotationMember {

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