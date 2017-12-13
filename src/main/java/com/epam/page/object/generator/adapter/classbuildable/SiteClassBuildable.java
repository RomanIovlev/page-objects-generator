package com.epam.page.object.generator.adapter.classbuildable;

import static com.epam.page.object.generator.util.StringUtils.firstLetterUp;
import static com.epam.page.object.generator.util.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.adapter.annotation.AnnotationMember;
import com.epam.page.object.generator.adapter.annotation.IJavaAnnotation;
import com.epam.page.object.generator.adapter.filed.IJavaField;
import com.epam.page.object.generator.adapter.annotation.JavaAnnotation;
import com.epam.page.object.generator.adapter.filed.JavaField;
import com.epam.page.object.generator.adapter.javaclass.IJavaClass;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import com.epam.page.object.generator.model.WebPage;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class SiteClassBuildable implements JavaClassBuildable {

    private List<WebPage> webPages;

    public SiteClassBuildable(List<WebPage> webPages) {
        this.webPages = webPages;
    }

    @Override
    public IJavaAnnotation buildAnnotation() {
        List<AnnotationMember> annotationMembers = new ArrayList<>();
        annotationMembers.add(new AnnotationMember("value", "$S", webPages.get(0).getDomainName()));

        return new JavaAnnotation(JSite.class, annotationMembers);
    }

    @Override
    public List<IJavaField> buildFields(String packageName) {
        List<IJavaField> fields = new ArrayList<>();

        for (WebPage webPage : webPages) {
            String fullClassName =
                packageName + ".page." + firstLetterUp(splitCamelCase(webPage.getTitle()));
            String fieldName = splitCamelCase(webPage.getTitle());
            List<AnnotationMember> pageAnnotations = new ArrayList<>();
            pageAnnotations
                .add(new AnnotationMember("url", "$S", webPage.getUrlWithoutDomain()));
            pageAnnotations.add(new AnnotationMember("title", "$S", webPage.getTitle()));
            IJavaAnnotation annotation = new JavaAnnotation(JPage.class, pageAnnotations);
            Modifier[] modifiers = new Modifier[]{PUBLIC, STATIC};

            fields.add(new JavaField(fullClassName, fieldName, annotation, modifiers));
        }

        return fields;
    }

    @Override
    public IJavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.visit(this);
    }
}
