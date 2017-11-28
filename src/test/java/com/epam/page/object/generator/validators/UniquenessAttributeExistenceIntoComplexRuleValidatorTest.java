package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class UniquenessAttributeExistenceIntoComplexRuleValidatorTest {

    private SearchRule ruleWithRootTitle =
        new SearchRule("type", "req", "root", "css", null, null);
    private SearchRule ruleWithRootTitleWithoutUniqueness =
        new SearchRule("type", null, "root", "css", null, null);
    private SearchRule ruleWithoutRootTitleWithoutUniqueness =
        new SearchRule("type", null, "title", null, "//input", null);
    private SearchRule ruleWithoutRootTitleWithUniqueness =
        new SearchRule("type", "req", "title", null, "//input", null);

    private SearchRule complexRuleWithOneRootWithOneUniqueness =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithRootTitle, ruleWithoutRootTitleWithoutUniqueness));
    private SearchRule complexRuleWithOneRootWithTwoUniquenesses =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithRootTitle, ruleWithoutRootTitleWithUniqueness));
    private SearchRule complexRuleWithoutRootTitleWithOneUniqueness =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithRootTitleWithoutUniqueness,
                ruleWithoutRootTitleWithUniqueness));
    private SearchRule complexRuleWithOneRootWithoutUniqueness =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithRootTitleWithoutUniqueness,
                ruleWithoutRootTitleWithoutUniqueness));
    private SearchRule complexRuleWithTwoRoots =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithRootTitle, ruleWithRootTitle));
    private SearchRule complexRuleWithNoRoots =
        new SearchRule(null, null, null, "css", null,
            Lists.newArrayList(ruleWithoutRootTitleWithoutUniqueness,
                ruleWithoutRootTitleWithoutUniqueness));


    private UniquenessAttributeExistenceIntoComplexRuleValidator sut;
    private ValidationContext context;
    private List<SearchRule> rules = Lists.newArrayList();

    @Before
    public void setUp() throws Exception {
        sut = new UniquenessAttributeExistenceIntoComplexRuleValidator();
    }


    @Test
    public void validate_TrueComplexSearchRuleWitOneRootWithOneUniqueness() {
        assertTrue(sut.isValid(complexRuleWithOneRootWithOneUniqueness, context));
    }

    @Test
    public void validate_FalseComplexRuleWithTwoRoots() {
        assertFalse(sut.isValid(complexRuleWithTwoRoots, context));
    }

    @Test
    public void validate_FalseComplexRuleWithoutRootTitleWithOneUniqueness() {
        assertFalse(sut.isValid(complexRuleWithoutRootTitleWithOneUniqueness, context));
    }

    //this test should be in another validator
//    @Test
//    public void validate_FalseComplexRuleWithTwoRootsWithOneUniqueness() {
//        assertFalse(sut.isValid(complexRuleWithTwoRootsWithOneUniqueness, context));
//    }

    @Test
    public void validate_FalseComplexRuleWithOneRootWithoutUniqueness() {
        assertFalse(sut.isValid(complexRuleWithOneRootWithoutUniqueness, context));
    }

    @Test
    public void validate_FalseComplexRuleWithNoRootsWithoutUniqueness() {
        assertFalse(sut.isValid(complexRuleWithNoRoots, context));
    }

    @Test
    public void validate_FalseComplexRuleWithOneRootsWithTwoUniquenesses() {
        assertFalse(sut.isValid(complexRuleWithOneRootWithTwoUniquenesses, context));
    }
}