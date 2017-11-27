package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.model.SearchRule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SectionAttributeExistenceValidatorTest {

    private SectionAttributeExistenceValidator sut;

    @Mock
    private SearchRule searchRule;

    @Mock
    private ValidationContext validationContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sut = new SectionAttributeExistenceValidator();
    }

    @Test
    public void isValid_TrueIfSectionAttributeExists() throws Exception {
        when(searchRule.getSection()).thenReturn("sdfas");

        assertTrue(sut.isValid(searchRule, validationContext));
    }

    @Test
    public void isValid_FalseIfEmptySectionAttribute() throws Exception {
        when(searchRule.getSection()).thenReturn("");

        assertFalse(sut.isValid(searchRule, validationContext));
    }

    @Test
    public void isValid_FalseIfNullSectionAttribute() throws Exception {
        when(searchRule.getSection()).thenReturn(null);

        assertFalse(sut.isValid(searchRule, validationContext));
    }

    @Test
    public void getPriority_LessThan50() throws Exception {
        assertTrue(sut.getPriority() < 50);
    }

}