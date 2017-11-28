package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

public class TitleExistenceIntoInnerRulesValidatorTest {

    private SearchRule ruleWithTitle =
        new SearchRule("type", "req", "root", "css", null, null);
    private SearchRule ruleWithoutTitle =
        new SearchRule("type", null, null, null, "//input", null);
    private SearchRule complexRuleWithValidInnerRules =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithTitle));
    private SearchRule complexRuleWithInvalidInnerRules =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithoutTitle, ruleWithTitle));

    private TitleExistenceIntoInnerRulesValidator sut;
    private ValidationContext context;

    @Before
    public void setUp() throws Exception {
        sut = new TitleExistenceIntoInnerRulesValidator();
    }

    @Test
    public void validate_TrueComplexSearchRuleWithValidInnerRules() {
        assertTrue(sut.isValid(complexRuleWithValidInnerRules, context));
    }

    @Test
    public void validate_FalseComplexRuleWithInvalidInnerRules() {
        assertFalse(sut.isValid(complexRuleWithInvalidInnerRules, context));
    }
}