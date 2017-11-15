package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UniquenessLocatorValidatorTest {

    private SearchRule uniquenessInnerSearchRuleWithXpath =
        new SearchRule(null, "text", "root", null, "//img[@class='dropimg']", null);
    private SearchRule notUniquenessInnerSearchRuleWithXpath =
        new SearchRule(null, "text", "root", null, "//button", null);

    private SearchRule uniquenessInnerSearchRuleWithCss =
        new SearchRule(null, "text", "root", null, "//img[@class='dropimg']", null);
    private SearchRule notUniquenessInnerSearchRuleWithCss =
        new SearchRule(null, "text", "root", null, "//button", null);

    private SearchRule uniquenessSearchRuleWithXpath =
        new SearchRule("dropdown", "text", null, null, "//img[@class='dropimg']", null);
    private SearchRule notUniquenessSearchRuleWithXpath =
        new SearchRule("dropdown", "text", null, null, "//button", null);
    private SearchRule complexRuleWithUniquenessXpathInnerRule =
        new SearchRule("dropdown", "text", null, null, "//img[@class='dropimg']",
            Lists.newArrayList(uniquenessInnerSearchRuleWithXpath));
    private SearchRule complexRuleWithNotUniquenessXpathInnerRule =
        new SearchRule("dropdown", "text", null, null, "//img[@class='dropimg']",
            Lists.newArrayList(notUniquenessInnerSearchRuleWithXpath));

    private SearchRule uniquenessSearchRuleWithCss =
        new SearchRule("dropdown", "text", null, "img.dropimg", null, null);
    private SearchRule notUniquenessSearchRuleWithCss =
        new SearchRule("dropdown", "text", null, "img", null, null);
    private SearchRule complexRuleWithUniquenessCssInnerRule =
        new SearchRule("dropdown", "text", null, "img.dropimg", null,
            Lists.newArrayList(uniquenessInnerSearchRuleWithCss));
    private SearchRule complexRuleWithNotUniquenessCssInnerRule =
        new SearchRule("dropdown", "text", null, "img.dropimg", null,
            Lists.newArrayList(notUniquenessInnerSearchRuleWithCss));

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
    public void validate_TrueUniquenessSearchRuleValidationWithXpath() throws Exception {
        assertTrue(sut.isValid(uniquenessSearchRuleWithXpath, context));
    }

    @Test
    public void validate_FalseNotUniquenessSearchRuleValidationWithXpath() throws Exception {
        assertFalse(sut.isValid(notUniquenessSearchRuleWithXpath, context));
    }

    @Test
    public void validate_TrueUniquenessInnerSearchRuleValidationWithXpath() throws Exception {
        assertTrue(sut.isValid(complexRuleWithUniquenessXpathInnerRule, context));
    }

    @Test
    public void validate_FalseNotUniquenessInnerSearchRuleValidationWithXpath() throws Exception {
        assertFalse(sut.isValid(complexRuleWithNotUniquenessXpathInnerRule, context));
    }

    @Test
    public void validate_TrueUniquenessSearchRuleValidationWithCss() throws Exception {
        assertTrue(sut.isValid(uniquenessSearchRuleWithCss, context));
    }

    @Test
    public void validate_FalseNotUniquenessSearchRuleValidationWithCss() throws Exception {
        assertFalse(sut.isValid(notUniquenessSearchRuleWithCss, context));
    }

    @Test
    public void validate_TrueUniquenessInnerSearchRuleValidationWithCss() throws Exception {
        assertTrue(sut.isValid(complexRuleWithUniquenessCssInnerRule, context));
    }

    @Test
    public void validate_FalseNotUniquenessInnerSearchRuleValidationWithCss() throws Exception {
        assertFalse(sut.isValid(complexRuleWithNotUniquenessCssInnerRule, context));
    }

    @After
    public void clear() {
        rules.clear();
    }

}