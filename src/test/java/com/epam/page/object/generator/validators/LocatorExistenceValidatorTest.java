package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class LocatorExistenceValidatorTest {

    private SearchRule ruleWithCss =
        new SearchRule("button", "req", null, "css", null, null);
    private SearchRule ruleWithXpath =
        new SearchRule("button", "req", null, null, "//input", null);
    private SearchRule ruleNoLocator =
        new SearchRule("button", "req", null, null, null, null);
    private SearchRule complexRuleWithNoLocatorInnerRule =
        new SearchRule(null, null, null, "css", null, Lists.newArrayList(ruleNoLocator));
    private SearchRule complexRuleWithLocatorsInnerRules =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithCss, ruleWithXpath));


    private LocatorExistenceValidator sut;
    private ValidationContext context;
    private List<SearchRule> rules = Lists.newArrayList();


    @Before
    public void setUp() throws Exception {
        sut = new LocatorExistenceValidator(SearchRuleTypeGroups.allExistingTypes);
    }

    @Test
    public void validate_allPassIfOnlyCssIsSet() throws Exception {
        context = new ValidationContext(Lists.newArrayList(ruleWithCss), null);
        sut.validate(context);
        assertEquals(ruleWithCss, context.getValidRules().get(0));
    }

    @Test
    public void validate_allPassIfOnlyXpathIsSet() throws Exception {
        context = new ValidationContext(Lists.newArrayList(ruleWithXpath), null);
        sut.validate(context);
        assertEquals(ruleWithXpath, context.getValidRules().get(0));
    }

    @Test
    public void validate_shouldAddSearchRuleToFailedIfNoLocatorSet() throws Exception {
        context = new ValidationContext(Lists.newArrayList(ruleNoLocator), null);
        sut.validate(context);
        assertEquals(0, context.getValidRules().size());
        assertEquals(1, context.getValidationResults().size());
        assertEquals(ruleNoLocator, context.getValidationResults().get(0).getSearchRule());
    }

    @Test
    public void validate_checkAllValidAndInvalidRulesLists() throws Exception {
        rules.add(ruleWithCss);
        rules.add(ruleWithXpath);
        rules.add(ruleNoLocator);

        context = new ValidationContext(rules, null);
        sut.validate(context);
        assertEquals(2, context.getValidRules().size());
    }

    @Test
    public void validate_TrueSearchRuleValidationWithCss() {
        assertTrue(sut.isValid(ruleWithCss, context));
    }

    @Test
    public void validate_TrueSearchRuleValidationWithXpath() {
        assertTrue(sut.isValid(ruleWithXpath, context));
    }

    @Test
    public void validate_FalseSearchRuleValidationNoLocators() {
        assertFalse(sut.isValid(ruleNoLocator, context));
    }

    @Test
    public void validate_TrueInnerSearchRulesValidation() {
        assertTrue(sut.isValid(complexRuleWithLocatorsInnerRules, context));
    }

    @Test
    public void validate_FalseInnerSearchRuleValidationNoLocators() {
        assertFalse(sut.isValid(complexRuleWithNoLocatorInnerRule, context));
    }
}