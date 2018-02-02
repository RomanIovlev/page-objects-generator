package com.epam.page.object.generator.validator.json;

import static org.junit.Assert.*;

import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import com.epam.page.object.generator.validator.ValidationResult;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

public class TitleOfComplexElementValidatorTest {

    private TitleOfComplexElementValidator sut;
    private XpathToCssTransformer xpathToCssTransformer = new XpathToCssTransformer();
    private SelectorUtils selectorUtils = new SelectorUtils();

    private ComplexInnerSearchRule expandRule = new ComplexInnerSearchRule(null,
        "expand", null, xpathToCssTransformer);
    private ComplexInnerSearchRule listRule = new ComplexInnerSearchRule(null,
        "list", null, xpathToCssTransformer);
    private ComplexInnerSearchRule wrongTitleRule = new ComplexInnerSearchRule(null,
        "wrongTitle", null, xpathToCssTransformer);

    private ComplexSearchRule validSearchRule = new ComplexSearchRule(SearchRuleType.DROPDOWN,
        Lists.newArrayList(expandRule, listRule),
        new ClassAndAnnotationPair(Dropdown.class, JDropdown.class), selectorUtils);

    private ComplexSearchRule validSearchRuleWithEmptyInnerRules =
        new ComplexSearchRule(SearchRuleType.DROPDOWN, Lists.newArrayList(),
        new ClassAndAnnotationPair(Dropdown.class, JDropdown.class), selectorUtils);
    
    private ComplexSearchRule SearchRuleWithWrongTitleInnerSearchRule = new ComplexSearchRule(
        SearchRuleType.DROPDOWN, Lists.newArrayList(wrongTitleRule),
        new ClassAndAnnotationPair(Dropdown.class, JDropdown.class), selectorUtils);

    private ComplexSearchRule SearchRuleWithBothWrongAndValidTitleInnerSearchRules =
        new ComplexSearchRule(SearchRuleType.DROPDOWN, Lists.newArrayList(listRule, wrongTitleRule),
            new ClassAndAnnotationPair(Dropdown.class, JDropdown.class), selectorUtils);

    @Before
    public void setUp() {
        sut = new TitleOfComplexElementValidator();
    }

    @Test
    public void visit_ValidSearchRule_Valid() {
        ValidationResult validationResult = sut.visit(validSearchRule);

        assertTrue(validationResult.isValid());
    }

    @Test
    public void visit_ValidSearchRuleWithEmptyInnerRules_Valid() {
        ValidationResult validationResult = sut.visit(validSearchRuleWithEmptyInnerRules);

        assertTrue(validationResult.isValid());
    }

    @Test
    public void visit_SearchRuleWithWrongTitleInnerSearchRule_Invalid() {
        ValidationResult validationResult = sut.visit(SearchRuleWithWrongTitleInnerSearchRule);

        assertFalse(validationResult.isValid());
    }

    @Test
    public void visit_SearchRuleWithBothWrongAndValidTitleInnerSearchRules_Invalid() {
        ValidationResult validationResult = sut.visit(SearchRuleWithBothWrongAndValidTitleInnerSearchRules);

        assertFalse(validationResult.isValid());
    }
}