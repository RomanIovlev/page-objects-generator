package com.epam.page.object.generator.validators.searchRuleWebValidators;

import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.webElementGroups.CommonWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.ComplexWebElementGroup;
import com.epam.page.object.generator.model.webElementGroups.FormWebElementGroup;
import com.epam.page.object.generator.model.webElements.FormWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.validators.ValidationResult;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UniquenessAttributeExistenceValidatorTest {

    private UniquenessAttributeExistenceValidator validator = new UniquenessAttributeExistenceValidator();

    @Mock
    private CommonWebElementGroup commonWebElementGroup;
    @Mock
    private ComplexWebElementGroup complexWebElementGroup;
    @Mock
    private WebElement element;
    @Mock
    private FormWebElement formWebElement;
    @Mock
    private CommonSearchRule commonSearchRule;
    @Mock
    private ComplexSearchRule complexSearchRule;
    @Mock
    private FormInnerSearchRule formInnerSearchRule;
    @Mock
    private FormWebElementGroup formWebElementGroup;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void visit_SuccessWithCommonWebElementGroup_OneUniquenessValue() {
        when(commonWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example value");
        ValidationResult result = validator.visit(commonWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_FailedWithCommonWebElementGroup_WrongValue() {
        when(commonWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(commonWebElementGroup.getSearchRule()).thenReturn(commonSearchRule);
        when(commonSearchRule.getUniqueness()).thenReturn("example_searchRule");
        when(element.getUniquenessValue()).thenReturn("");

        ValidationResult result = validator.visit(commonWebElementGroup);
        assertFalse(result.isValid());
    }

    @Test
    public void visit_SuccessWithComplexWebElementGroup_OneUniquenessValue() {
        when(complexWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example value");
        ValidationResult result = validator.visit(complexWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_FailedWithComplexWebElementGroup_WrongValue() {
        when(complexWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(complexWebElementGroup.getSearchRule()).thenReturn(complexSearchRule);
        when(complexSearchRule.getUniqueness()).thenReturn("some_complexSearchRule");
        when(element.getUniquenessValue()).thenReturn("");

        ValidationResult result = validator.visit(complexWebElementGroup);
        assertFalse(result.isValid());
    }

    @Test
    public void visit_SuccessWithFormWebElementGroup_OneUniquenessValue() {
        when(complexWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(element));
        when(element.getUniquenessValue()).thenReturn("example value");
        ValidationResult result = validator.visit(complexWebElementGroup);
        assertTrue(result.isValid());
    }

    @Test
    public void visit_FailedWithFormWebElementGroup_WrongValue() {
       when(formWebElementGroup.getWebElements()).thenReturn(Lists.newArrayList(formWebElement));
       when(formWebElement.getUniquenessValue()).thenReturn("");
       when(formWebElement.getSearchRule()).thenReturn(formInnerSearchRule);
       when(formWebElement.getUniquenessValue()).thenReturn("");

        ValidationResult result = validator.visit(formWebElementGroup);
        assertFalse(result.isValid());
    }

}