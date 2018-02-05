package com.epam.page.object.generator.builder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.adapter.AnnotationMember;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.classbuildable.FormClass;
import com.epam.page.object.generator.adapter.classbuildable.PageClass;
import com.epam.page.object.generator.adapter.classbuildable.SiteClass;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.adapter.JavaClass;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.util.SearchRuleType;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Modifier;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.support.FindBy;

public class JavaClassBuilderTest {

    private String PACKAGE = "package";

    @Mock
    private AnnotationMember annotationMember;
    @Mock
    private JavaField javaField;
    private List<JavaField> javaFields;

    @Mock
    private SiteClass siteClass;
    private JavaAnnotation siteAnnotation;

    @Mock
    private PageClass pageClass;

    @Mock
    private FormClass formClass;
    @Mock
    private FormWebElementGroup formWebElementGroup;
    private FormSearchRule formSearchRule = new FormSearchRule("myForm", SearchRuleType.FORM,
        new Selector("css", "div[class=form]"), null,
        new ClassAndAnnotationPair(Form.class, FindBy.class));

    private JavaClassBuilder javaClassBuilder = new JavaClassBuilder(PACKAGE);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        javaFields = Lists.newArrayList(javaField);
        siteAnnotation = new JavaAnnotation(JSite.class, Lists.newArrayList(annotationMember));
    }

    @Test
    public void build_SiteClass_Valid() {
        when(siteClass.buildAnnotation()).thenReturn(siteAnnotation);
        when(siteClass.buildFields(PACKAGE)).thenReturn(javaFields);

        JavaClass javaClass = javaClassBuilder.build(siteClass);

        assertEquals(PACKAGE + ".site", javaClass.getPackageName());
        assertEquals("Site", javaClass.getClassName());
        assertEquals(WebSite.class, javaClass.getSuperClass());
        assertEquals(siteAnnotation, javaClass.getAnnotation());
        assertEquals(javaFields, javaClass.getFieldsList());
        assertEquals(Modifier.PUBLIC, javaClass.getModifier());

        verify(siteClass).buildFields(eq(PACKAGE));
    }

    @Test
    public void build_PageClass_Valid() {
        when(pageClass.getTitle()).thenReturn("Title example");
        when(pageClass.buildAnnotation()).thenReturn(null);
        when(pageClass.buildFields(PACKAGE)).thenReturn(javaFields);

        JavaClass javaClass = javaClassBuilder.build(pageClass);

        assertEquals(PACKAGE + ".page", javaClass.getPackageName());
        assertEquals("TitleExample", javaClass.getClassName());
        assertEquals(WebPage.class, javaClass.getSuperClass());
        assertEquals(null, javaClass.getAnnotation());
        assertEquals(javaFields, javaClass.getFieldsList());
        assertEquals(Modifier.PUBLIC, javaClass.getModifier());

        verify(pageClass).buildFields(eq(PACKAGE));
    }

    @Test
    public void build_FormClassNullAnnotation_Valid() {
        when(formClass.getFormWebElementGroup()).thenReturn(formWebElementGroup);
        when(formWebElementGroup.getSearchRule()).thenReturn(formSearchRule);
        when(formClass.buildAnnotation()).thenReturn(null);
        when(formClass.buildFields(PACKAGE)).thenReturn(javaFields);

        JavaClass javaClass = javaClassBuilder.build(formClass);

        assertEquals(PACKAGE + ".form", javaClass.getPackageName());
        assertEquals("MyForm", javaClass.getClassName());
        assertEquals(Form.class, javaClass.getSuperClass());
        assertEquals(null, javaClass.getAnnotation());
        assertEquals(Collections.emptyList(), javaClass.getFieldsList());
        assertEquals(Modifier.PUBLIC, javaClass.getModifier());

        verify(formClass).buildFields(eq(PACKAGE + ".form"));
    }
}