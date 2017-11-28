package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

public class DuplicateTitleInInnerSearchRulesValidatorTest {

    private SearchRule ruleWithRootTitle =
        new SearchRule("type", "req", "root", "css", null, null);
    private SearchRule ruleWithTitleTitle =
        new SearchRule("type", null, "title", null, "//input", null);
    private SearchRule complexRuleWithOneRoot =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithRootTitle, ruleWithTitleTitle));
    private SearchRule complexRuleWithTwoRoots =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithRootTitle, ruleWithRootTitle));
    private SearchRule complexRuleWithTwoTitles =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithTitleTitle, ruleWithTitleTitle));

    private DuplicateTitleInInnerSearchRulesValidator sut;
    private ValidationContext context;

    @Before
    public void setUp() throws Exception {
        sut = new DuplicateTitleInInnerSearchRulesValidator();
    }

    @Test
    public void validate_TrueComplexSearchRuleWitOneRoot() {
        assertTrue(sut.isValid(complexRuleWithOneRoot, context));
    }

    @Test
    public void validate_FalseComplexRuleWithTwoRoots() {
        assertFalse(sut.isValid(complexRuleWithTwoRoots, context));
    }

    @Test
    public void validate_FalseComplexRuleWithTwoTitles() {
        assertFalse(sut.isValid(complexRuleWithTwoTitles, context));
    }
}