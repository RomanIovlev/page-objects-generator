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
public class SearchRuleValidatorTest {

    private SearchRuleValidator sut;

    private Set<String> supportedTypes = new HashSet<>();

    private SearchRule ruleWithLocator =
            new SearchRule("button", "req", null, "css", null, null);
    private SearchRule ruleNoLocator =
            new SearchRule("button", "req", null, null, null, null);

    private List<SearchRule> searchRules = new ArrayList<>();
    private ValidationContext context;
    private List<String> urls = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        supportedTypes.add("button");
        supportedTypes.add("input");

        urls.add("https://www.google.ru");

        context = new ValidationContext(searchRules, urls);

        sut = new SearchRuleValidator(context);

        sut.setCheckLocatorsUniqueness(false);
    }

    @Test
    public void validateSearchRules_Success() throws Exception {
        searchRules.add(ruleWithLocator);
        sut.validate();

        Assert.assertEquals(1, context.getValidRules().size());
        Assert.assertEquals(2, context.getValidationResults().size());
    }

    @Test(expected = ValidationException.class)
    public void validateSearchRules_NotLocatorExist() throws Exception {
        searchRules.add(ruleNoLocator);
        sut.validate();
    }

    @After
    public void clear() {
        searchRules.clear();
    }
}