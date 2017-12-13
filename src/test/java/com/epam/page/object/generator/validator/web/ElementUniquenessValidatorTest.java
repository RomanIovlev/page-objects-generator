package com.epam.page.object.generator.validator.web;

import com.epam.page.object.generator.model.webgroup.CommonWebElementGroup;
import com.epam.page.object.generator.model.webgroup.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webgroup.FormWebElementGroup;
import com.epam.page.object.generator.model.webelement.WebElement;
import com.epam.page.object.generator.validator.ValidationResult;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ElementUniquenessValidatorTest {

    @Mock
    private CommonWebElementGroup commonWebElementGroup;
    @Mock
    private ComplexWebElementGroup complexWebElementGroup;
    @Mock
    private FormWebElementGroup formWebElementGroup;
    @Mock
    private WebElement element;

    private ElementUniquenessValidator validator = new ElementUniquenessValidator();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void visit_SuccessWithCommonWebElementGroup_OneUniquenessValue() {
        when(commonWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example_value");

        ValidationResult result = validator.visit(commonWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_SuccessWithCommonWebElementGroup_UniquenessValues() {
        when(commonWebElementGroup.getWebElements())
            .thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("example_value1")
            .thenReturn("example_value2");

        ValidationResult result = validator.visit(commonWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_FailedWithCommonWebElementGroup_DuplicateValues() {
        when(commonWebElementGroup.getWebElements())
            .thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("duplicate_value")
            .thenReturn("duplicate_value");

        ValidationResult result = validator.visit(commonWebElementGroup);
        assertFalse(result.isValid());
    }

    @Test
    public void visit_SuccessWithComplexWebElementGroup_OneUniquenessValue() {
        when(complexWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example_value");

        ValidationResult result = validator.visit(complexWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_SuccessWithComplexWebElementGroup_UniquenessValues() {
        when(commonWebElementGroup.getWebElements())
            .thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("example_value1")
            .thenReturn("example_value2");

        ValidationResult result = validator.visit(complexWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_FailedWithComplexWebElementGroup_DuplicateValues() {
        when(complexWebElementGroup.getWebElements())
            .thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("duplicate_value")
            .thenReturn("duplicate_value");

        ValidationResult result = validator.visit(complexWebElementGroup);
        assertFalse(result.isValid());
    }

    @Test
    public void visit_SuccessWithFormWebElementGroup_OneUniquenessValue() {
        when(formWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example_value");

        ValidationResult result = validator.visit(formWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_SuccessWithFormWebElementGroup_UniquenessValues() {
        when(formWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("example_value1")
            .thenReturn("example_value2");

        ValidationResult result = validator.visit(formWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_FailedWithFormWebElementGroup_DuplicateValues() {
        when(formWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("duplicate_value")
            .thenReturn("duplicate_value");

        ValidationResult result = validator.visit(formWebElementGroup);
        assertFalse(result.isValid());
    }
}