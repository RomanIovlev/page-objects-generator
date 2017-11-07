package com.epam.page.object.generator.validators;

import com.epam.page.object.generator.errors.LocatorExistenceException;
import com.epam.page.object.generator.errors.TypeSupportedException;
import com.epam.page.object.generator.model.SearchRule;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ValidationContextTest {
    private SearchRule searchRule1 = new SearchRule();
    private SearchRule searchRule2 = new SearchRule();
    private SearchRule searchRule3 = new SearchRule();

    private RuntimeException locatorExistenceException
            = new LocatorExistenceException("No xpath or css");
    private RuntimeException typeSupportedException
            = new TypeSupportedException("Type is not supported");

    private ValidationContext sut;

    @Before
    public void setUp() throws Exception {
        sut = new ValidationContext(
                Lists.newArrayList(
                    searchRule1,
                    searchRule2,
                    searchRule3),
                null);
    }


    @Test
    public void addRulesToInvalid() {
        sut.addRuleToInvalid(searchRule1, typeSupportedException);
        sut.addRuleToInvalid(searchRule2, locatorExistenceException);
        sut.addRuleToInvalid(searchRule3, typeSupportedException);

        Map<RuntimeException, List<SearchRule>> invalidRules = Maps.newHashMap();

        invalidRules.put(typeSupportedException, Lists.newArrayList(searchRule1, searchRule3));
        invalidRules.put(locatorExistenceException, Lists.newArrayList(searchRule2));

        assertTrue(invalidRules.equals(sut.getNotValidRulesWithExceptions()));

     }


    @Test
    public void getNotValidRuleList() throws Exception {
        sut.addRuleToInvalid(searchRule1, typeSupportedException);
        sut.addRuleToInvalid(searchRule2, locatorExistenceException);
        sut.addRuleToInvalid(searchRule3, typeSupportedException);
        sut.addRuleToInvalid(searchRule3, locatorExistenceException);
        sut.addRuleToInvalid(searchRule1, locatorExistenceException);

        List<SearchRule> invalidRules = Lists.newArrayList(searchRule2, searchRule1, searchRule3);

        assertTrue((invalidRules.size() == sut.getNotValidRules().size()) &&
                invalidRules.containsAll(sut.getNotValidRules()));
    }

    @After
    public void clear() {
        sut.getNotValidRulesWithExceptions().clear();
    }

}