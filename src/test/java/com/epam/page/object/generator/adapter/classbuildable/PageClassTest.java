package com.epam.page.object.generator.adapter.classbuildable;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.junit.Assert.assertEquals;

import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.adapter.AnnotationMember;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.databuilder.WebPageTestDataBuilder;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

public class PageClassTest {

    private String packageName = "test";

    private WebPage webPage = WebPageTestDataBuilder.getJdiWebPage();
    private WebElementGroupFieldBuilder webElementGroupFieldBuilder = new WebElementGroupFieldBuilder();
    private PageClass pageClass = new PageClass(webPage, webElementGroupFieldBuilder);

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
    private List<JavaField> expectedFields = Lists.newArrayList(
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.common.Button", "calculate",
            expectedButtonAnnotation, new Modifier[]{PUBLIC}),
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.common.Label", "water",
            expectedLabelAnnotation, new Modifier[]{PUBLIC}),
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown", "colors",
            expectedDropdownAnnotation, new Modifier[]{PUBLIC}));

    @Test
    public void buildFields() {

        List<JavaField> actualFields = pageClass.buildFields(packageName);

        for (int i = 0; i < actualFields.size(); i++) {
            JavaField actualField = actualFields.get(0);
            JavaField expectedField = expectedFields.get(0);
            assertEquals(expectedField.getFieldName(), actualField.getFieldName());
            assertEquals(expectedField.getFullFieldClass(), actualField.getFullFieldClass());
            assertEquals(expectedField.getModifiers(), actualField.getModifiers());

            JavaAnnotation actualAnnotation = actualField.getAnnotation();
            JavaAnnotation expectedAnnotation = expectedField.getAnnotation();
            assertEquals(expectedAnnotation.getAnnotationClass(),
                actualAnnotation.getAnnotationClass());
            for (int j = 0; j < actualAnnotation.getAnnotationMembers().size(); j++) {
                AnnotationMember actualAnnotationMember = actualAnnotation.getAnnotationMembers()
                    .get(j);
                AnnotationMember expectedAnnotationMember = expectedAnnotation
                    .getAnnotationMembers().get(j);

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
    public void buildAnnotation() {
        JavaAnnotation actualAnnotation = pageClass.buildAnnotation();
        assertEquals(null, actualAnnotation);
    }
}