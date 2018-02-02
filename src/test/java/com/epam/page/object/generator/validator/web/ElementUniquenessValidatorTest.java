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
    public void visit_CommonWebElementGroupUniquenessValue_Valid() {
        when(commonWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example_value");

        ValidationResult result = validator.visit(commonWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_CommonWebElementGroupUniquenessValues_Valid() {
        when(commonWebElementGroup.getWebElements())
            .thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("example_value1")
            .thenReturn("example_value2");

        ValidationResult result = validator.visit(commonWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_CommonWebElementGroupDuplicateValues_Invalid() {
        when(commonWebElementGroup.getWebElements())
            .thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("duplicate_value")
            .thenReturn("duplicate_value");

        ValidationResult result = validator.visit(commonWebElementGroup);
        assertFalse(result.isValid());
    }

    @Test
    public void visit_ComplexWebElementGroupUniquenessValue_Valid() {
        when(complexWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example_value");

        ValidationResult result = validator.visit(complexWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_ComplexWebElementGroupUniquenessValues_Valid() {
        when(commonWebElementGroup.getWebElements())
            .thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("example_value1")
            .thenReturn("example_value2");

        ValidationResult result = validator.visit(complexWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_ComplexWebElementGroupDuplicateValues_Invalid() {
        when(complexWebElementGroup.getWebElements())
            .thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("duplicate_value")
            .thenReturn("duplicate_value");

        ValidationResult result = validator.visit(complexWebElementGroup);
        assertFalse(result.isValid());
    }

    @Test
    public void visit_FormWebElementGroupUniquenessValue_Valid() {
        when(formWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example_value");

        ValidationResult result = validator.visit(formWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_FormWebElementGroupUniquenessValues_Valid() {
        when(formWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("example_value1")
            .thenReturn("example_value2");

        ValidationResult result = validator.visit(formWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_FormWebElementGroupDuplicateValues_Invalid() {
        when(formWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element, element));
        when(element.getUniquenessValue()).thenReturn("duplicate_value")
            .thenReturn("duplicate_value");

        ValidationResult result = validator.visit(formWebElementGroup);
        assertFalse(result.isValid());
    }
}