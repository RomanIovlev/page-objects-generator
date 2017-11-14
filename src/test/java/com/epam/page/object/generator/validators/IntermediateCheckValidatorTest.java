package com.epam.page.object.generator.validators;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.errors.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IntermediateCheckValidatorTest {

    private IntermediateCheckValidator sut;

    @Mock
    private ValidationContext validationContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sut = new IntermediateCheckValidator();
    }

    @Test
    public void validate_success() throws Exception {
        when(validationContext.hasInvalidRules()).thenReturn(false);

        sut.validate(validationContext);

        verify(validationContext).hasInvalidRules();
    }

    @Test(expected = ValidationException.class)
    public void validate_FalseWhenHasInvalidRules() throws Exception {
        when(validationContext.hasInvalidRules()).thenReturn(true);

        sut.validate(validationContext);

        verify(validationContext).hasInvalidRules();
    }


}