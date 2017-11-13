package com.epam.page.object.generator.validators;


import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

//TODO rewrite all tests with creation Validators
public class ValidatorsStarterTest {

    private ValidatorsStarter sut;

    private Set<String> supportedTypes = new HashSet<>();

    private SearchRule ruleJsonValid =
            new SearchRule("button", "req", null, "css", null, null);
    private SearchRule ruleJsonInvalid =
            new SearchRule("button", "req", null, null, null, null);

    private List<SearchRule> searchRules = new ArrayList<>();
    private List<String> urls = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        supportedTypes.add("button");
        supportedTypes.add("input");

        urls.add("https://www.google.com");

        sut = new ValidatorsStarter();

        sut.setCheckLocatorsUniqueness(true);
    }

    @Test
    public void validateSearchRules_Success() throws Exception {
        searchRules.add(ruleJsonValid);
        sut.validate(searchRules, urls);

        Assert.assertEquals(1, sut.getValidationContext().getValidRules().size());
        Assert.assertEquals(4, sut.getValidationContext().getValidationResults().size());
    }

    @Test(expected = ValidationException.class)
    public void validateSearchRules_NotLocatorExist() throws Exception {
        searchRules.add(ruleJsonInvalid);
        sut.validate(searchRules, urls);
    }

    @After
    public void clear() {
        searchRules.clear();
    }
}