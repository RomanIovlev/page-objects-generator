package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

public class RootExistenceValidatorTest {

    private SearchRule ruleWithRootTitle =
        new SearchRule("type", "req", "root", "css", null, null);
    private SearchRule ruleWithoutRootTitle =
        new SearchRule("type", null, "title", null, "//input", null);
    private SearchRule complexRuleWithOneRoot =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithRootTitle, ruleWithoutRootTitle));
    private SearchRule complexRuleWithNoRoots =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithoutRootTitle, ruleWithoutRootTitle));

    private RootExistenceValidator sut;
    private ValidationContext context;

    @Before
    public void setUp() throws Exception {
        sut = new RootExistenceValidator();
    }

    @Test
    public void validate_TrueComplexSearchRuleWitOneRoot() {
        assertTrue(sut.isValid(complexRuleWithOneRoot, context));
    }

    @Test
    public void validate_FalseComplexRuleWithNoRoots() {
        assertFalse(sut.isValid(complexRuleWithNoRoots, context));
    }
}
