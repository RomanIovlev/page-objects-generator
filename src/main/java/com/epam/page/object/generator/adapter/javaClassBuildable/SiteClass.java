package com.epam.page.object.generator.adapter.javaClassBuildable;

import static com.epam.page.object.generator.utils.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.adapter.javaAnnotations.AnnotationMember;
import com.epam.page.object.generator.adapter.javaAnnotations.IJavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.IJavaField;
import com.epam.page.object.generator.adapter.javaAnnotations.JavaAnnotation;
import com.epam.page.object.generator.adapter.javaFields.JavaField;
import com.epam.page.object.generator.adapter.javaClasses.IJavaClass;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.model.WebPage;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class SiteClass implements JavaClassBuildable {

    private List<WebPage> webPages;

    public SiteClass(List<WebPage> webPages) {
        this.webPages = webPages;
    }

    public List<WebPage> getWebPages() {
        return webPages;
    }

    @Override
    public IJavaAnnotation getAnnotation() {
        List<AnnotationMember> annotationMembers = new ArrayList<>();
        annotationMembers.add(new AnnotationMember("value", "$S", webPages.get(0).getDomainName()));

        return new JavaAnnotation(JSite.class, annotationMembers);
    }

    @Override
    public List<IJavaField> getFields() {
        List<IJavaField> fields = new ArrayList<>();

        for (WebPage webPage : webPages) {
            String className = firstLetterUp(splitCamelCase(webPage.getTitle()));
            String fieldName = splitCamelCase(webPage.getTitle());
            List<AnnotationMember> pageAnnotations = new ArrayList<>();
            pageAnnotations
                .add(new AnnotationMember("webPage", "$S", webPage.getUrlWithoutDomain()));
            pageAnnotations.add(new AnnotationMember("title", "$S", webPage.getTitle()));
            IJavaAnnotation annotation = new JavaAnnotation(JPage.class, pageAnnotations);
            Modifier[] modifiers = new Modifier[]{PUBLIC, STATIC};

            fields.add(new JavaField(className, fieldName, annotation, modifiers));
        }

        return fields;
    }

    @Override
    public IJavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.visit(this);
    }
}
