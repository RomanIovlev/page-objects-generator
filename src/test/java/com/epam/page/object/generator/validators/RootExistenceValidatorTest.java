package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class RootExistenceValidatorTest {
    private SearchRule ruleWithCss =
        new SearchRule("type", "req", null, "css", null, null);
    private SearchRule ruleWithXpath =
        new SearchRule("type", null, null, null, "//input", null);
    private SearchRule complexRuleWithOneRoot =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithCss, ruleWithXpath));
    private SearchRule complexRuleWithTwoRoots =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithCss, ruleWithCss));
    private SearchRule complexRuleWithNoRoots =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithXpath, ruleWithXpath));


    private RootExistenceValidator sut;
    private ValidationContext context;
    private List<SearchRule> rules = Lists.newArrayList();


    @Before
    public void setUp() throws Exception {
        sut = new RootExistenceValidator();
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
    public void validate_FalseComplexRuleWithNoRoots() {
        assertFalse(sut.isValid(complexRuleWithNoRoots, context));
    }
}
