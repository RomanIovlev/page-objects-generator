package com.epam.page.object.generator.validators;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AbstractValidatorTest {

    private AbstractValidator sut;

    @Mock
    private ValidationContext validationContext;

    @Mock
    private SupportedTypesContainer bc;

    private SearchRule ruleWithButtonType =
        new SearchRule("button", null, null, null, null, null);

    private SearchRule ruleWithDropdownType =
        new SearchRule("dropdown", null, null, null, null, null);

    private SearchRule ruleWithTabsType =
        new SearchRule("tabs", null, null, null, null, null);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validate_NotValidateNotSupportedTypesRule() throws Exception {
        when(validationContext.getValidRules()).thenReturn(Lists.newArrayList(ruleWithButtonType));

        sut = new TypeSupportedValidator(Sets.newHashSet(SearchRuleType.TABS), bc);

        sut.validate(validationContext);

        verify(validationContext, never()).addValidationResult(Mockito.any());
    }

    @Test
    public void validate_NotValidateWithNullSupportedTypesRule() throws Exception {
        when(validationContext.getValidRules()).thenReturn(Lists.newArrayList(ruleWithButtonType));

        sut = new TypeSupportedValidator();

        sut.validate(validationContext);

        verify(validationContext, never()).addValidationResult(Mockito.any());
    }

    @Test
    public void validate_ValidateRightSupportedTypesRule() throws Exception {
        when(validationContext.getValidRules()).thenReturn(Lists.newArrayList(ruleWithButtonType));

        sut = new TypeSupportedValidator(Sets.newHashSet(SearchRuleType.BUTTON), bc);

        sut.validate(validationContext);

        verify(validationContext, times(1)).addValidationResult(Mockito.any());
    }

    @Test
    public void validate_ValidateIfSupportedTypesContainsType_ALL() {
        when(validationContext.getValidRules()).thenReturn(Lists.newArrayList(ruleWithButtonType));

        sut = new TypeSupportedValidator(Sets.newHashSet(SearchRuleType.ALL), bc);

        sut.validate(validationContext);

        verify(validationContext, times(1)).addValidationResult(Mockito.any());
    }

    @Test
    public void validate_ValidateMultipleSearchRulesWithAllSupportedTypes() {
        when(validationContext.getValidRules()).thenReturn(Lists.newArrayList(ruleWithButtonType,
            ruleWithDropdownType, ruleWithTabsType));

        sut = new LocatorExistenceValidator(Sets.newHashSet(SearchRuleType.TABS,
            SearchRuleType.BUTTON, SearchRuleType.DROPDOWN));

        sut.validate(validationContext);

        verify(validationContext, times(3)).addValidationResult(Mockito.any());
    }

    @Test
    public void validate_ValidateOnlySearchRulesWithSupportedTypes() {
        when(validationContext.getValidRules()).thenReturn(Lists.newArrayList(ruleWithButtonType,
            ruleWithDropdownType, ruleWithTabsType));

        sut = new LocatorExistenceValidator(Sets.newHashSet(SearchRuleType.TABS,
            SearchRuleType.BUTTON));

        sut.validate(validationContext);

        verify(validationContext, times(2)).addValidationResult(Mockito.any());
    }

    @Test
    public void validate_NotValidateSearchRulesWithNotSupportedTypes() {
        when(validationContext.getValidRules()).thenReturn(Lists.newArrayList(ruleWithButtonType,
            ruleWithDropdownType, ruleWithTabsType));

        sut = new LocatorExistenceValidator(Sets.newHashSet(SearchRuleType.ELEMENTS,
            SearchRuleType.CHECKBOX));

        sut.validate(validationContext);

        verify(validationContext, never()).addValidationResult(Mockito.any());
    }


}