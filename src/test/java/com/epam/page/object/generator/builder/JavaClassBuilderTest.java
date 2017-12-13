package com.epam.page.object.generator.builder;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.page.object.generator.adapter.annotation.AnnotationMember;
import com.epam.page.object.generator.adapter.annotation.JavaAnnotation;
import com.epam.page.object.generator.adapter.classbuildable.FormClassBuildable;
import com.epam.page.object.generator.adapter.classbuildable.PageClassBuildable;
import com.epam.page.object.generator.adapter.classbuildable.SiteClassBuildable;
import com.epam.page.object.generator.adapter.javaclass.IJavaClass;
import com.epam.page.object.generator.adapter.filed.IJavaField;
import com.epam.page.object.generator.adapter.filed.JavaField;
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
    private List<IJavaField> javaFields = Lists.newArrayList(javaField);

    @Mock
    private SiteClassBuildable siteClassBuildable;
    private JavaAnnotation siteAnnotation = new JavaAnnotation(JSite.class,
        Lists.newArrayList(annotationMember));

    @Mock
    private PageClassBuildable pageClassBuildable;

    @Mock
    private FormClassBuildable formClassBuildable;
    @Mock
    private FormWebElementGroup formWebElementGroup;
    private FormSearchRule formSearchRule = new FormSearchRule("myForm", SearchRuleType.FORM,
        new Selector("css", "div[class=form]"), null,
        new ClassAndAnnotationPair(Form.class, FindBy.class));

    private JavaClassBuilder javaClassBuilder = new JavaClassBuilder(PACKAGE);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void visitSiteClassBuildable() {
        when(siteClassBuildable.buildAnnotation()).thenReturn(siteAnnotation);
        when(siteClassBuildable.buildFields(PACKAGE)).thenReturn(javaFields);

        IJavaClass javaClass = javaClassBuilder.visit(siteClassBuildable);

        assertEquals(PACKAGE + ".site", javaClass.getPackageName());
        assertEquals("Site", javaClass.getClassName());
        assertEquals(WebSite.class, javaClass.getSuperClass());
        assertEquals(siteAnnotation, javaClass.getAnnotation());
        assertEquals(javaFields, javaClass.getFieldsList());
        assertEquals(Modifier.PUBLIC, javaClass.getModifier());

        verify(siteClassBuildable).buildAnnotation();
        verify(siteClassBuildable).buildFields(eq(PACKAGE));
    }

    @Test
    public void visitPageClassBuildable() {
        when(pageClassBuildable.getTitle()).thenReturn("Title example");
        when(pageClassBuildable.buildAnnotation()).thenReturn(null);
        when(pageClassBuildable.buildFields(PACKAGE)).thenReturn(javaFields);

        IJavaClass javaClass = javaClassBuilder.visit(pageClassBuildable);

        assertEquals(PACKAGE + ".page", javaClass.getPackageName());
        assertEquals("TitleExample", javaClass.getClassName());
        assertEquals(WebPage.class, javaClass.getSuperClass());
        assertEquals(null, javaClass.getAnnotation());
        assertEquals(javaFields, javaClass.getFieldsList());
        assertEquals(Modifier.PUBLIC, javaClass.getModifier());

        verify(pageClassBuildable).buildAnnotation();
        verify(pageClassBuildable).buildFields(eq(PACKAGE));
    }

    @Test
    public void visitFormClassBuildable() {
        when(formClassBuildable.getFormWebElementGroup()).thenReturn(formWebElementGroup);
        when(formWebElementGroup.getSearchRule()).thenReturn(formSearchRule);
        when(formClassBuildable.buildAnnotation()).thenReturn(null);
        when(formClassBuildable.buildFields(PACKAGE)).thenReturn(javaFields);

        IJavaClass javaClass = javaClassBuilder.visit(formClassBuildable);

        assertEquals(PACKAGE + ".form", javaClass.getPackageName());
        assertEquals("MyForm", javaClass.getClassName());
        assertEquals(Form.class, javaClass.getSuperClass());
        assertEquals(null, javaClass.getAnnotation());
        assertEquals(Collections.emptyList(), javaClass.getFieldsList());
        assertEquals(Modifier.PUBLIC, javaClass.getModifier());

        verify(formClassBuildable).buildAnnotation();
        verify(formClassBuildable).buildFields(eq(PACKAGE + ".form"));
    }
}