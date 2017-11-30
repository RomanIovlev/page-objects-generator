package com.epam.page.object.generator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.validators.oldValidators.TypeSupportedValidator;
import java.util.ArrayList;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

public class TypeSupportedValidatorTest {

    private TypeSupportedValidator sut;

    private SearchRule innerRule = new SearchRule(null, "req", "css", null, null,
        new ArrayList<>());

    private SearchRule ruleWithRightType =
        new SearchRule("button", "req", "css", null, null, Lists.newArrayList(innerRule));
    private SearchRule ruleWithWrongType =
        new SearchRule("type", "req", "css", null, null, Lists.newArrayList(innerRule));
    private SearchRule complexRuleWithRigthType =
        new SearchRule("button", "req", "css", null, null, Lists.newArrayList(innerRule));
    private SearchRule complexRuleWithWrongType =
        new SearchRule("type", "req", "css", null, null, Lists.newArrayList(innerRule));

    @Before
    public void setUp() throws Exception {
        sut = new TypeSupportedValidator(null, new SupportedTypesContainer());
    }

    @Test
    public void validate_TrueSearchRuleValidationWithRightType() throws Exception {
        assertTrue(sut.isValid(ruleWithRightType, null));
    }

    @Test
    public void validate_FalseSearchRuleValidationWithWrongType() throws Exception {
        assertFalse(sut.isValid(ruleWithWrongType, null));
    }

    @Test
    public void validate_TrueComplexSearchRuleValidationWithRightType() throws Exception {
        assertTrue(sut.isValid(complexRuleWithRigthType, null));
    }

    @Test
    public void validate_FalseComplexSearchRuleValidationWithWrongType() throws Exception {
        assertFalse(sut.isValid(complexRuleWithWrongType, null));
    }

}