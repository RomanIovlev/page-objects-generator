package com.epam.page.object.generator.validators;


import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class ValidatorsStarterTest {

    private ValidatorsStarter sut;

    private Set<String> supportedTypes = new HashSet<>();

    private SearchRule ruleValid =
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
        searchRules.add(ruleValid);
        List<SearchRule> validationResults = sut.validate(searchRules, urls);

        Assert.assertEquals(searchRules.size(), validationResults.size());

        //IntermediateCheckValidator and UrlsValidator do not generate validationResult,
        // that why we should compare amount of validationResults with validators.size() - 2
        Assert.assertEquals(sut.getValidators().size() - 2,
                sut.getValidationContext().getValidationResults().size());
    }

    @Test(expected = ValidationException.class)
    public void validateSearchRules_NotLocatorExist() throws Exception {
        searchRules.add(ruleJsonInvalid);
        sut.validate(searchRules, urls);
    }

    @Test
    public void setCheckLocatorsUniquenessTrue() {
        sut.setCheckLocatorsUniqueness(true);

        assertTrue(sut.getValidators()
                .stream()
                .anyMatch(validator -> validator instanceof UniquenessLocatorValidator));

    }

    @Test
    public void setCheckLocatorsUniquenessFalse() {
        sut.setCheckLocatorsUniqueness(false);

        assertTrue(sut.getValidators()
                .stream()
                .noneMatch(validator -> validator instanceof UniquenessLocatorValidator));

    }


    @After
    public void clear() {
        searchRules.clear();
    }
}