package com.epam.page.object.generator.adapter.classbuildable;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static org.junit.Assert.*;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.adapter.annotation.AnnotationMember;
import com.epam.page.object.generator.adapter.annotation.IJavaAnnotation;
import com.epam.page.object.generator.adapter.annotation.JavaAnnotation;
import com.epam.page.object.generator.adapter.filed.IJavaField;
import com.epam.page.object.generator.adapter.filed.JavaField;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.testDataBuilders.WebPageTestDataBuilder;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class SiteClassBuildableTest {

    private String packageName = "test";

    private List<WebPage> webPages = Lists.newArrayList(WebPageTestDataBuilder.getJdiWebPage());
    private SiteClassBuildable siteClassBuildable = new SiteClassBuildable(webPages);

    private JavaAnnotation expectedAnnotation = new JavaAnnotation(JSite.class,
        Lists.newArrayList(new AnnotationMember("value", "$S", "epam.github.io")));

    private JavaAnnotation expectedFieldAnnotation = new JavaAnnotation(JPage.class,
        Lists.newArrayList(new AnnotationMember("url", "$S", "/JDI/metals-colors.html"),
            new AnnotationMember("title", "$S", "Metal and Colors")));
    private JavaField expectedField = new JavaField("test.page.MetalAndColors",
        "metalAndColors", expectedFieldAnnotation, new Modifier[]{PUBLIC, STATIC});
    private List<IJavaField> expectedFields = Lists.newArrayList(expectedField);

    @Test
    public void buildFields() {
        List<IJavaField> actualFields = siteClassBuildable.buildFields(packageName);

        assertEquals(expectedFields.size(), actualFields.size());

        IJavaField actualSiteField = actualFields.get(0);
        assertEquals(expectedField.getFieldName(), actualSiteField.getFieldName());
        assertEquals(expectedField.getFullFieldClass(), actualSiteField.getFullFieldClass());
        assertEquals(expectedField.getModifiers(), actualSiteField.getModifiers());

        IJavaAnnotation actualFieldAnnotation = actualSiteField.getAnnotation();
        assertEquals(expectedFieldAnnotation.getAnnotationClass(),
            actualFieldAnnotation.getAnnotationClass());
        assertEquals(expectedFieldAnnotation.getAnnotationMembers().size(),
            expectedFieldAnnotation.getAnnotationMembers().size());

        for (int i = 0; i < actualFieldAnnotation.getAnnotationMembers().size(); i++) {
            AnnotationMember expectedAnnotationMember = expectedFieldAnnotation
                .getAnnotationMembers().get(i);
            AnnotationMember actualAnnotationMember = actualFieldAnnotation
                .getAnnotationMembers().get(i);
            assertEquals(expectedAnnotationMember.getName(),
                actualAnnotationMember.getName());
            assertEquals(expectedAnnotationMember.getFormat(),
                actualAnnotationMember.getFormat());
            assertEquals(expectedAnnotationMember.getArg(),
                actualAnnotationMember.getArg());
            assertEquals(expectedAnnotationMember.getAnnotation(),
                actualAnnotationMember.getAnnotation());
        }
    }

    @Test
    public void buildAnnotation() {
        IJavaAnnotation actualAnnotation = siteClassBuildable.buildAnnotation();

        assertEquals(expectedAnnotation.getAnnotationClass(),
            actualAnnotation.getAnnotationClass());
        AnnotationMember expectedAnnotationMember = expectedAnnotation.getAnnotationMembers()
            .get(0);
        AnnotationMember actualAnnotationMember = actualAnnotation.getAnnotationMembers().get(0);
        assertEquals(expectedAnnotationMember.getName(), actualAnnotationMember.getName());
        assertEquals(expectedAnnotationMember.getFormat(), actualAnnotationMember.getFormat());
        assertEquals(expectedAnnotationMember.getAnnotation(),
            actualAnnotationMember.getAnnotation());
        assertEquals(expectedAnnotationMember.getArg(), actualAnnotationMember.getArg());
    }
}