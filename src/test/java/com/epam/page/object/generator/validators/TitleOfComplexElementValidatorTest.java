package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TitleOfComplexElementValidatorTest {

    private SearchRule validCommonSearchRule = new SearchRule("dropdown", null, null,
        "div.dropdown", null, null);

    private SearchRule validInnerSearchRule1 = new SearchRule(null, "text", "root", null,
        "//button[@class='dropbtn']", null);
    private SearchRule validInnerSearchRule2 = new SearchRule(null, "text", "jexpand", null,
        "//button[@class='dropbtn']", null);

    private SearchRule validComplexSearchRule = new SearchRule("dropdown", null, null,
        "div.dropdown", null,
        Lists.newArrayList(validInnerSearchRule1, validInnerSearchRule2));

    private SearchRule validInnerSearchRule3 = new SearchRule(null, "text", "root", null,
        "//button[@class='dropbtn']", null);
    private SearchRule invalidInnerSearchRule4 = new SearchRule(null, "text", "BAMBOLAILO", null,
        "//button[@class='dropbtn']", null);

    private SearchRule invalidComplexSearchRule = new SearchRule("dropdown", null, null,
        "div.dropdown", null, Lists.newArrayList(validInnerSearchRule3, invalidInnerSearchRule4));

    private TitleOfComplexElementValidator sut;

    @Before
    public void setUp() throws Exception {
        sut = new TitleOfComplexElementValidator();
    }

    @Test
    public void validate_TrueCommonSearchRule() throws Exception {
        assertTrue(sut.isValid(validCommonSearchRule, null));
    }

    @Test
    public void validate_TrueComplexSearchRuleWithValidInnerSearchRules() throws Exception {
        assertTrue(sut.isValid(validComplexSearchRule, null));

    }

    @Test
    public void validate_FalseComplexSearchRuleWithInvalidInnerSearchRules() throws Exception {
        assertFalse(sut.isValid(invalidComplexSearchRule, null));

    }
}