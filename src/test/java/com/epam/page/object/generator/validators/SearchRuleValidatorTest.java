package com.epam.page.object.generator.validators;


import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class SearchRuleValidatorTest {

    private SearchRuleValidator sut;

    private Set<String> supportedTypes = new HashSet<>();

    private List<SearchRule> searchRules = new ArrayList<>();

    private SearchRule innerRule = new SearchRule(null, "req", "css", null, null, searchRules);

    private SearchRule ruleWithLocator =
            new SearchRule("button", "req", null, "css", null, searchRules);
    private SearchRule ruleNoLocator =
            new SearchRule("button", "req", null, null, null, searchRules);

    private ValidationContext context;
    private List<String> urls = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        supportedTypes.add("button");
        supportedTypes.add("input");

        urls.add("https://www.google.ru");

        context = new ValidationContext(searchRules, urls);

        sut = new SearchRuleValidator(supportedTypes);

        sut.setCheckLocatorsUniqueness(false);
    }

    @Test
    public void validateSearchRules_Success() throws Exception {
        searchRules.add(ruleWithLocator);
        sut.validate(searchRules, urls);

        Assert.assertEquals(1, context.getValidRules().size());
    }

    @Test(expected = ValidationException.class)
    public void validateSearchRules_NotLocatorExist() throws Exception {
        searchRules.add(ruleNoLocator);
        sut.validate(searchRules, urls);
    }

    @After
    public void clear(){
        searchRules.clear();
    }
}
