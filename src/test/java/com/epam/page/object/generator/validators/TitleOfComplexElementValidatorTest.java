package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TitleOfComplexElementValidatorTest {

    private SearchRule validCommonSearchRule = new SearchRule("dropdown", null, null, "div.dropdown", null, null);

    private SearchRule validInnerSearchRule1 = new SearchRule(null, "text", "root", null, "//button[@class='dropbtn']", null);
    private SearchRule validInnerSearchRule2 = new SearchRule(null, "text", "jexpand", null, "//button[@class='dropbtn']", null);
    private List<SearchRule> innerSearchRules = Arrays.asList(validInnerSearchRule1, validInnerSearchRule2);
    private SearchRule validComplexSearchRule = new SearchRule("dropdown", null, null, "div.dropdown", null, innerSearchRules);

    private SearchRule validInnerSearchRule3 = new SearchRule(null, "text", "root", null, "//button[@class='dropbtn']", null);
    private SearchRule validInnerSearchRule4 = new SearchRule(null, "text", "BAMBOLAILO", null, "//button[@class='dropbtn']", null);
    private List<SearchRule> innerSearchRules2 = Arrays.asList(validInnerSearchRule3, validInnerSearchRule4);
    private SearchRule invalidComplexSearchRule1 = new SearchRule("dropdown", null, null, "div.dropdown", null, innerSearchRules2);

    private TitleOfComplexElementValidator sut;

    @Before
    public void setUp() throws Exception {
        sut = new TitleOfComplexElementValidator();
    }

    @Test
    public void trueComplexElementTitleOfComplexElementValidatorTest() throws Exception {
        assertTrue(sut.isValid(validComplexSearchRule, null));

    } @Test
    public void trueCommonElementTitleOfComplexElementValidatorTest() throws Exception {
        assertTrue(sut.isValid(validCommonSearchRule, null));

    }@Test
    public void falseComplexElementTitleOfComplexElementValidatorTest() throws Exception {
        assertFalse(sut.isValid(invalidComplexSearchRule1, null));

    }





}