package com.epam.page.object.generator.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockitoAnnotationsMockAnnotationProcessor;

public class AbstractValidatorTest {

    private AbstractValidator sut;

    @Mock
    private ValidationContext validationContext;

    private SearchRule ruleWithButtonType =
        new SearchRule("button", null, null, null, null,null);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(validationContext.getValidRules()).thenReturn(Lists.newArrayList(ruleWithButtonType));
    }

    @Test
    public void validate_NotValidateNotSupportedTypesRule() throws Exception {
        sut = new TypeSupportedValidator(Sets.newHashSet(SearchRuleType.TABS));

        sut.validate(validationContext);

        verify(validationContext, never()).addValidationResult(Mockito.any());
    }

    @Test
    public void validate_NotValidateWithNullSupportedTypesRule() throws Exception {
        sut = new TypeSupportedValidator();

        sut.validate(validationContext);

        verify(validationContext, never()).addValidationResult(Mockito.any());
    }

    @Test
    public void validate_ValidateRightSupportedTypesRule() throws Exception {
        sut = new TypeSupportedValidator(Sets.newHashSet(SearchRuleType.BUTTON));

        sut.validate(validationContext);

        verify(validationContext, times(1)).addValidationResult(Mockito.any());
    }
}