package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniquenessLocatorValidatorTest {

    private SearchRule uniguenessInnerSearchRule =
        new SearchRule(null, "text", "root", null, "//img[@class='dropimg']", null);
    private SearchRule notUniguenessInnerSearchRule =
        new SearchRule(null, "text", "root", null, "//button", null);

    private SearchRule uniquenessSearchRuleWithXpath =
        new SearchRule("dropdown", "text", null, null, "//img[@class='dropimg']", null);
    private SearchRule notUniquenessSearchRuleWithXpath =
        new SearchRule("dropdown", "text", null, null, "//button", null);
    private SearchRule complexRuleWithUniquenessXpathInnerRule =
        new SearchRule("dropdown", "text", null, null, null,
            Lists.newArrayList(uniguenessInnerSearchRule));
    private SearchRule complexRuleWithNotUniquenessXpathInnerRule =
        new SearchRule("dropdown", "text", null, null, null,
            Lists.newArrayList(notUniguenessInnerSearchRule));

    private UniquenessLocatorValidator sut;
    private ValidationContext context;
    private List<SearchRule> rules = Lists.newArrayList();
    private List<String> urls = Lists.newArrayList();

    @Before
    public void setUp() throws Exception {
        sut = new UniquenessLocatorValidator();
        urls.add("https://www.w3schools.com/css/css_dropdowns.asp");
        context = new ValidationContext(rules, urls);
    }

    @Test
    public void validate_TrueUniquenessSearchRuleValidation() throws Exception {
        assertTrue(sut.isValid(uniquenessSearchRuleWithXpath, context));
    }

    @Test
    public void validate_FalseNotUniquenessSearchRuleValidation() throws Exception {
        assertFalse(sut.isValid(notUniquenessSearchRuleWithXpath, context));
    }

    @Test
    public void validate_TrueUniquenessInnerSearchRuleValidation() throws Exception {
        assertTrue(sut.isValid(complexRuleWithUniquenessXpathInnerRule, context));
    }

    @Test
    public void validate_FalseNotUniquenessInnerSearchRuleValidation() throws Exception {
        assertFalse(sut.isValid(complexRuleWithNotUniquenessXpathInnerRule, context));
    }

    @After
    public void clear() {
        rules.clear();
    }

}