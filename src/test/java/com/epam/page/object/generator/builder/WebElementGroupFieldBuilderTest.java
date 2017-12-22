package com.epam.page.object.generator.builder;

import static javax.lang.model.element.Modifier.PUBLIC;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.model.webelement.WebElement;
import com.epam.page.object.generator.model.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.model.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import java.util.Iterator;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.support.FindBy;

public class WebElementGroupFieldBuilderTest {

    private XpathToCssTransformer transformer = new XpathToCssTransformer();
    private SelectorUtils selectorUtils = new SelectorUtils();

    @Mock
    private JavaAnnotation javaAnnotation;

    @Mock
    private WebElement webElement;
    private List<WebElement> webElements;
    private Iterator<WebElement> webElementIterator;

    @Mock
    private CommonWebElementGroup commonWebElementGroup;
    private CommonSearchRule commonSearchRule = new CommonSearchRule("text", SearchRuleType.BUTTON,
        new Selector("css", "input[type=submit]"),
        new ClassAndAnnotationPair(Button.class, FindBy.class), transformer, selectorUtils);

    @Mock
    private ComplexWebElementGroup complexWebElementGroup;
    private ComplexInnerSearchRule complexInnerSearchRule1 = new ComplexInnerSearchRule("text",
        "root", new Selector("css", ".myClass"), transformer);
    private ComplexInnerSearchRule complexInnerSearchRule2 = new ComplexInnerSearchRule(null,
        "list", new Selector("xpath", "//div"), transformer);
    private List<ComplexInnerSearchRule> innerSearchRules = Lists
        .newArrayList(complexInnerSearchRule1, complexInnerSearchRule2);
    private ComplexSearchRule complexSearchRule = new ComplexSearchRule(SearchRuleType.DROPDOWN,
        innerSearchRules, new ClassAndAnnotationPair(Dropdown.class, JDropdown.class),
        selectorUtils);

    @Mock
    private FormWebElementGroup formWebElementGroup;
    private FormSearchRule formSearchRule = new FormSearchRule("myForm", SearchRuleType.FORM,
        new Selector("css", "div[class=form]"), null,
        new ClassAndAnnotationPair(Form.class, FindBy.class));

    private WebElementGroupFieldBuilder webElementGroupFieldBuilder = new WebElementGroupFieldBuilder();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webElementIterator = mock(Iterator.class);
        when(webElementIterator.hasNext()).thenReturn(true, false);
        when(webElementIterator.next()).thenReturn(webElement);

        webElements = mock(List.class);
        when(webElements.iterator()).thenReturn(webElementIterator);
    }

    @Test
    public void visitCommonWebElementGroup() {
        when(commonWebElementGroup.getSearchRule()).thenReturn(commonSearchRule);
        when(commonWebElementGroup.getWebElements()).thenReturn(webElements);
        when(webElement.getUniquenessValue()).thenReturn("Submit");
        when(commonWebElementGroup.getAnnotation(any(Class.class), any(WebElement.class)))
            .thenReturn(javaAnnotation);

        List<JavaField> javaFields = webElementGroupFieldBuilder.build(commonWebElementGroup);
        JavaField javaField = javaFields.get(0);

        assertEquals("com.epam.jdi.uitests.web.selenium.elements.common.Button",
            javaField.getFullFieldClass());
        assertEquals("submit", javaField.getFieldName());
        assertEquals(javaAnnotation, javaField.getAnnotation());
        assertEquals(1, javaField.getModifiers().length);
        assertEquals(PUBLIC, javaField.getModifiers()[0]);
    }

    @Test
    public void visitComplexWebElementGroup() {
        when(complexWebElementGroup.getSearchRule()).thenReturn(complexSearchRule);
        when(complexWebElementGroup.getWebElements()).thenReturn(webElements);
        when(webElement.getUniquenessValue()).thenReturn("Dropdown list");
        when(complexWebElementGroup.getAnnotation(any(Class.class), any(WebElement.class)))
            .thenReturn(javaAnnotation);

        List<JavaField> javaFields = webElementGroupFieldBuilder.build(complexWebElementGroup);
        JavaField javaField = javaFields.get(0);

        assertEquals("com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown",
            javaField.getFullFieldClass());
        assertEquals("dropdownList", javaField.getFieldName());
        assertEquals(javaAnnotation, javaField.getAnnotation());
        assertEquals(1, javaField.getModifiers().length);
        assertEquals(PUBLIC, javaField.getModifiers()[0]);
    }

    @Test
    public void visitFormWebElementGroup() {
        when(formWebElementGroup.getSearchRule()).thenReturn(formSearchRule);
        when(formWebElementGroup.getAnnotation(any(Class.class))).thenReturn(javaAnnotation);

        List<JavaField> javaFields = webElementGroupFieldBuilder.build(formWebElementGroup, "package");
        JavaField javaField = javaFields.get(0);

        assertEquals("package.form.MyForm", javaField.getFullFieldClass());
        assertEquals("myForm", javaField.getFieldName());
        assertEquals(javaAnnotation, javaField.getAnnotation());
        assertEquals(1, javaField.getModifiers().length);
        assertEquals(PUBLIC, javaField.getModifiers()[0]);
    }
}