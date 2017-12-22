package com.epam.page.object.generator.adapter.classbuildable;

import static com.epam.page.object.generator.util.StringUtils.splitCamelCase;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static org.apache.commons.lang3.StringUtils.capitalize;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.adapter.AnnotationMember;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.builder.JavaClassBuilder;
import com.epam.page.object.generator.model.WebPage;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteClass implements JavaClassBuildable {

    private List<WebPage> webPages;

    private final static Logger logger = LoggerFactory.getLogger(SiteClass.class);

    public SiteClass(List<WebPage> webPages) {
        this.webPages = webPages;
    }

    @Override
    public JavaAnnotation buildAnnotation() {
        List<AnnotationMember> annotationMembers = new ArrayList<>();
        AnnotationMember annotationMember = new AnnotationMember("value", "$S", webPages.get(0).getDomainName());
        annotationMembers.add(annotationMember);
        logger.debug("Add " + annotationMember);
        return new JavaAnnotation(JSite.class, annotationMembers);
    }

    @Override
    public List<JavaField> buildFields(String packageName) {
        List<JavaField> fields = new ArrayList<>();

        for (WebPage webPage : webPages) {
            String fullClassName =
                packageName + ".page." + capitalize(splitCamelCase(webPage.getTitle()));
            String fieldName = splitCamelCase(webPage.getTitle());
            List<AnnotationMember> pageAnnotations = new ArrayList<>();
            pageAnnotations
                .add(new AnnotationMember("url", "$S", webPage.getUrlWithoutDomain()));
            pageAnnotations.add(new AnnotationMember("title", "$S", webPage.getTitle()));
            JavaAnnotation annotation = new JavaAnnotation(JPage.class, pageAnnotations);
            Modifier[] modifiers = new Modifier[]{PUBLIC, STATIC};

            JavaField javaField = new JavaField(fullClassName, fieldName, annotation, modifiers);
            logger.debug("Add field = " + javaField);
            fields.add(javaField);
        }
        return fields;
    }

    @Override
    public JavaClass accept(JavaClassBuilder javaClassBuilder) {
        return javaClassBuilder.build(this);
    }

    @Override
    public String toString() {
        return "SiteClass";
    }
}
