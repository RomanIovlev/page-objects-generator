package com.epam.page.object.generator.validators;

import static org.junit.Assert.*;

import com.epam.page.object.generator.model.SearchRule;
import org.junit.Before;
import org.junit.Test;
import org.testng.collections.Lists;

public class TypeSupportedValidatorTest {

    private TypeSupportedValidator sut;
    private SearchRule innerRule = new SearchRule(null, "req", "css", null, null);

    private SearchRule ruleWithRightType =
            new SearchRule("button", "req", "css", null, null);
    private SearchRule ruleWithWrongType =
            new SearchRule("type", "req", "css", null, null);
    private SearchRule complexRuleWithRigthType =
            new SearchRule("button", "req", "css", null, Lists.newArrayList(innerRule));
    private SearchRule complexRuleWithWrongType =
            new SearchRule("type", "req", "css", null, Lists.newArrayList(innerRule));

    @Before
    public void setUp() throws Exception {
        sut = new TypeSupportedValidator();
    }

    @Test
    public void validate_TrueSearchRuleValidationWithRightType() throws Exception {
        assertTrue(sut.validate(ruleWithRightType));
    }

    @Test
    public void validate_FalseSearchRuleValidationWithWrongType() throws Exception {
        assertFalse(sut.validate(ruleWithWrongType));
    }

    @Test
    public void validate_TrueComplexSearchRuleValidationWithRightType() throws Exception {
        assertTrue(sut.validate(complexRuleWithRigthType));
    }

    @Test
    public void validate_FalseComplexSearchRuleValidationWithWrongType() throws Exception {
        assertFalse(sut.validate(complexRuleWithWrongType));
    }

}