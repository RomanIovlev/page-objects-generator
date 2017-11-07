package com.epam.page.object.generator.validators;


import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class SearchRuleValidatorTest {

    private SearchRuleValidator sut;

    private Set<String> supportedTypes = new HashSet<>();

    SearchRule ruleWithLocator = new SearchRule("type", "req", "css", null, null);
    SearchRule ruleNoLocator = new SearchRule("type", "req", null, null, null);

    private List<SearchRule> searchRules = new ArrayList<>();

    private List<String> urls = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        supportedTypes.add("button");
        supportedTypes.add("input");

        urls.add("https://www.google.ru");

        ValidationContext context = new ValidationContext(searchRules, urls);

        sut = new SearchRuleValidator(supportedTypes, context);

        sut.setCheckLocatorsUniqueness(false);
    }

    @Test
    public void validateSearchRules_Success() throws Exception {
        searchRules.add(ruleWithLocator);
        sut.validate();
    }

    @Test(expected = ValidationException.class)
    public void validateSearchRules_NotLocatorExist() throws Exception {
        searchRules.add(ruleNoLocator);
        sut.validate();
    }

    @After
    public void clear(){
        searchRules.clear();
    }
}
