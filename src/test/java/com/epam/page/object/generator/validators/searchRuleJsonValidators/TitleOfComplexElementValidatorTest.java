package com.epam.page.object.generator.validators.searchRuleJsonValidators;

import static org.junit.Assert.*;

import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.validators.ValidationResult;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

public class TitleOfComplexElementValidatorTest {

    private TitleOfComplexElementValidator sut;

    private ComplexInnerSearchRule expandRule = new ComplexInnerSearchRule(null,
        "expand", null);
    private ComplexInnerSearchRule listRule = new ComplexInnerSearchRule(null,
        "list", null);
    private ComplexInnerSearchRule wrongTitleRule = new ComplexInnerSearchRule(null,
        "wrongTitle", null);

    private ComplexSearchRule validSearchRule = new ComplexSearchRule(SearchRuleType.DROPDOWN,
        Lists.newArrayList(expandRule, listRule),
        new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));

    private ComplexSearchRule validSearchRuleWithEmptyInnerRules =
        new ComplexSearchRule(SearchRuleType.DROPDOWN, Lists.newArrayList(),
        new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));
    
    private ComplexSearchRule SearchRuleWithWrongTitleInnerSearchRule = new ComplexSearchRule(
        SearchRuleType.DROPDOWN, Lists.newArrayList(wrongTitleRule),
        new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));

    private ComplexSearchRule SearchRuleWithBothWrongAndValidTitleInnerSearchRules =
        new ComplexSearchRule(SearchRuleType.DROPDOWN, Lists.newArrayList(listRule, wrongTitleRule),
            new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));

    @Before
    public void setUp() {
        sut = new TitleOfComplexElementValidator();
    }

    @Test
    public void visit_Success() {
        ValidationResult validationResult = sut.visit(validSearchRule);

        assertTrue(validationResult.isValid());
    }

    @Test
    public void visit_SuccessEmptyInnerRules() {
        ValidationResult validationResult = sut.visit(validSearchRuleWithEmptyInnerRules);

        assertTrue(validationResult.isValid());
    }

    @Test
    public void visit_FailedSearchRuleWithWrongTitleInnerSearchRule() {
        ValidationResult validationResult = sut.visit(SearchRuleWithWrongTitleInnerSearchRule);

        assertFalse(validationResult.isValid());
    }

    @Test
    public void visit_FailedSearchRuleWithBothWrongAndValidTitleInnerSearchRules() {
        ValidationResult validationResult = sut.visit(SearchRuleWithBothWrongAndValidTitleInnerSearchRules);

        assertFalse(validationResult.isValid());
    }
}