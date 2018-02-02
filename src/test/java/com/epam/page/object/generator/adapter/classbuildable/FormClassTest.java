package com.epam.page.object.generator.adapter.classbuildable;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.junit.Assert.*;

import com.epam.page.object.generator.adapter.AnnotationMember;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.databuilder.WebPageTestDataBuilder;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.util.SelectorUtils;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

public class FormClassTest {

    private String packageName = "test";

    private SelectorUtils selectorUtils = new SelectorUtils();

    private FormWebElementGroup formWebElementGroup = (FormWebElementGroup) WebPageTestDataBuilder
        .getJdiContactFormWebPage().getWebElementGroups().get(0);

    private FormClass formClass = new FormClass(formWebElementGroup,
        selectorUtils);

    private List<JavaField> expectedJavaFields = Lists.newArrayList(
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.common.Input", "name",
            new JavaAnnotation(FindBy.class,
                Lists.newArrayList(new AnnotationMember("css", "$S", "input[id='Name']")
                )), new Modifier[]{PUBLIC}),
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.common.Input", "lastname",
            new JavaAnnotation(FindBy.class,
                Lists.newArrayList(new AnnotationMember("css", "$S", "input[id='LastName']")
                )), new Modifier[]{PUBLIC}),
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.common.Button", "submit",
            new JavaAnnotation(FindBy.class,
                Lists.newArrayList(new AnnotationMember("xpath", "$S",
                    "//button[@type='submit'[@class='uui-button dark-blue' and text()='Submit']")
                )), new Modifier[]{PUBLIC}),
        new JavaField("com.epam.jdi.uitests.web.selenium.elements.common.Button", "calculate",
            new JavaAnnotation(FindBy.class,
                Lists.newArrayList(new AnnotationMember("xpath", "$S",
                    "//button[@type='submit'[@class='uui-button dark-blue' and text()='Calculate']")
                )), new Modifier[]{PUBLIC})
    );


    @Test
    public void build_JavaAnnotationFields_Valid() {
        List<JavaField> actualJavaFields = formClass.buildFields(packageName);
        assertEquals(expectedJavaFields.size(), actualJavaFields.size());
        for (int i = 0; i < actualJavaFields.size(); i++) {
            JavaField actualField = actualJavaFields.get(i);
            JavaField expectedField = expectedJavaFields.get(i);
            assertEquals(expectedField.getFullFieldClass(), actualField.getFullFieldClass());
            assertEquals(expectedField.getFieldName(), actualField.getFieldName());
            assertEquals(expectedField.getModifiers(), actualField.getModifiers());

            JavaAnnotation actualAnnotation = actualField.getAnnotation();
            JavaAnnotation expectedAnnotation = expectedField.getAnnotation();
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
    public void build_JavaAnnotation_Null() {
        JavaAnnotation javaAnnotation = formClass.buildAnnotation();
        assertEquals(null, javaAnnotation);
    }

}