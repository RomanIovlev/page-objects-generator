package com.epam.page.object.generator.adapter.classbuildable;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.junit.Assert.assertEquals;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.adapter.annotation.AnnotationMember;
import com.epam.page.object.generator.adapter.annotation.IJavaAnnotation;
import com.epam.page.object.generator.adapter.annotation.JavaAnnotation;
import com.epam.page.object.generator.adapter.filed.IJavaField;
import com.epam.page.object.generator.adapter.filed.JavaField;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.testDataBuilders.WebPageTestDataBuilder;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

public class PageClassBuildableTest {

    private String packageName = "test";

    private WebPage webPage = WebPageTestDataBuilder.getJdiWebPage();
    private WebElementGroupFieldBuilder webElementGroupFieldBuilder = new WebElementGroupFieldBuilder();
    private PageClassBuildable pageClassBuildable = new PageClassBuildable(webPage,
        webElementGroupFieldBuilder);

    private JavaAnnotation expectedAnnotation = null;

    private JavaAnnotation expectedButtonAnnotation = new JavaAnnotation(FindBy.class,
        Lists.newArrayList(new AnnotationMember("css", "$S",
            "button[type=submit][id=calculate-button][text='Calculate']")));
    private JavaAnnotation expectedLabelAnnotation = new JavaAnnotation(FindBy.class,
        Lists.newArrayList(new AnnotationMember("xpath", "$S",
            "//label[@for='g1' and text()='Water']")));
    private JavaAnnotation expectedDropdownAnnotation = new JavaAnnotation(JDropdown.class,
        Lists.newArrayList(
            new AnnotationMember("root", "$L", new JavaAnnotation(FindBy.class, Lists.newArrayList(
                new AnnotationMember("css", "$S",
                    "button[data-id=colors-dropdown][title='Colors']")))),
            new AnnotationMember("list", "$L", new JavaAnnotation(FindBy.class, Lists.newArrayList(
                new AnnotationMember("xpath", "$S",
                    "//ul[@class='dropdown-menu inner selectpicker']"))))));
    private List<IJavaField> expectedFields = Lists.newArrayList(
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.common.Button", "calculate",
            expectedButtonAnnotation, new Modifier[]{PUBLIC}),
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.common.Label", "water",
            expectedLabelAnnotation, new Modifier[]{PUBLIC}),
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown", "colors",
            expectedDropdownAnnotation, new Modifier[]{PUBLIC}));

    @Test
    public void buildFields() {

        List<IJavaField> actualFields = pageClassBuildable.buildFields(packageName);

        for (int i = 0; i < actualFields.size(); i++) {
            IJavaField actualField = actualFields.get(0);
            IJavaField expectedField = expectedFields.get(0);
            assertEquals(expectedField.getFieldName(), actualField.getFieldName());
            assertEquals(expectedField.getFullFieldClass(), actualField.getFullFieldClass());
            assertEquals(expectedField.getModifiers(), actualField.getModifiers());

            IJavaAnnotation actualAnnotation = actualField.getAnnotation();
            IJavaAnnotation expectedAnnotation = expectedField.getAnnotation();
            assertEquals(expectedAnnotation.getAnnotationClass(),
                actualAnnotation.getAnnotationClass());
            for (int j = 0; j < actualAnnotation.getAnnotationMembers().size(); j++) {
                AnnotationMember actualAnnotationMember = actualAnnotation.getAnnotationMembers()
                    .get(j);
                AnnotationMember expectedAnnotationMember = expectedAnnotation
                    .getAnnotationMembers()
                    .get(j);

                assertEquals(expectedAnnotationMember.getName(), actualAnnotationMember.getName());
                assertEquals(expectedAnnotationMember.getFormat(),
                    actualAnnotationMember.getFormat());
                assertEquals(expectedAnnotationMember.getAnnotation(),
                    actualAnnotationMember.getAnnotation());
                assertEquals(expectedAnnotationMember.getArg(), actualAnnotationMember.getArg());
            }
        }
    }

    @Test
    public void buildAnnotation(){
        IJavaAnnotation actualAnnotation = pageClassBuildable.buildAnnotation();
        assertEquals(expectedAnnotation, actualAnnotation);
    }
}