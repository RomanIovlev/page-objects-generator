package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.validators.oldValidators.ValidationContext;
import com.epam.page.object.generator.validators.web.UniquenessAttributeExistenceValidator;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class UniquenessAttributeExistenceValidatorTest {

    private SearchRule validCommonSearchRule =
        new SearchRule(null, "alt", null, null, "//img[@class='dropimg']", null);
    private SearchRule invalidCommonSearchRule =
        new SearchRule(null, "text", null, null, "//img[@class='dropimg']", null);

    private SearchRule validInnerSearchRule =
        new SearchRule(null, "alt", "root", null, "//img[@class='dropimg']", null);
    private SearchRule invalidInnerSearchRule =
        new SearchRule(null, "text", "root", null, "//img[@class='dropimg']", null);

    private SearchRule validComplexSearchRule =
        new SearchRule("dropdown", null, null, null, "//img[@class='dropimg']",
            Lists.newArrayList(validInnerSearchRule));
    private SearchRule invalidComplexSearchRule =
        new SearchRule("dropdown", null, null, null, "//img[@class='dropimg']",
            Lists.newArrayList(invalidInnerSearchRule));

    private SearchRule invalidComplexSearchRuleWithValidAndInvalidInnerRules =
        new SearchRule("dropdown", null, null, null, "//img[@class='dropimg']",
            Lists.newArrayList(validInnerSearchRule, invalidInnerSearchRule));

    private UniquenessAttributeExistenceValidator sut;
    private ValidationContext validationContext;
    private List<SearchRule> rules = Lists.newArrayList();
    private List<String> urls = Lists.newArrayList();


    @Before
    public void setUp() throws Exception {
        sut = new UniquenessAttributeExistenceValidator();
        urls.add("https://www.w3schools.com/css/css_dropdowns.asp");
        validationContext = new ValidationContext(rules, urls);
    }

    @Test
    public void validate_TrueUniquenessCommonSearchRule() {
        assertTrue(sut.isValid(validCommonSearchRule, validationContext));
    }

    @Test
    public void validate_FalseNotUniquenessCommonSearchRule() {
        assertFalse(sut.isValid(invalidCommonSearchRule, validationContext));
    }

    @Test
    public void validate_TrueUniquenessComplexSearchRule() {
        assertTrue(sut.isValid(validComplexSearchRule, validationContext));
    }

    @Test
    public void validate_FalseNotUniquenessComplexSearchRule() {
        assertFalse(sut.isValid(invalidComplexSearchRule, validationContext));
    }

    @Test
    public void validate_FalseNotUniquenessComplexSearchRuleWithValidAndInvalidInnerRules() {
        assertFalse(
            sut.isValid(invalidComplexSearchRuleWithValidAndInvalidInnerRules, validationContext));
    }

}