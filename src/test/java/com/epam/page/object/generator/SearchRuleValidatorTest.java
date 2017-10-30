package com.epam.page.object.generator;


import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.validators.SearchRuleValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SearchRuleValidatorTest {

    private SearchRuleValidator sut;

    private Set<String> supportedTypes = new HashSet<>();

    @Mock
    private SearchRule searchRule;

    private List<SearchRule> searchRules = new ArrayList<>();

    private List<String> urls = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        supportedTypes.add("button");
        supportedTypes.add("input");

        searchRules.add(searchRule);

        urls.add("https://www.google.ru");

        sut = new SearchRuleValidator(supportedTypes);

        sut.setCheckLocatorsUniqueness(false);

        when(searchRule.getType()).thenReturn("button");
        when(searchRule.getXpath()).thenReturn("//input[@type='submit']");
    }

    @Test
    public void validateSearchRules_success() throws Exception {
        when(searchRule.extractElementsFromWebSite(any(String.class))).thenReturn(new Elements(new Element("button")));

        sut.setCheckLocatorsUniqueness(true);
        sut.validate(searchRules, urls);
        verify(searchRule).extractElementsFromWebSite(urls.get(0));
    }

    @Test(expected = NotUniqueSelectorsException.class)
    public void validateSearchRules_NotUniqueLocatorFound() throws Exception {
        when(searchRule.extractElementsFromWebSite(any(String.class))).thenReturn(new Elements(new Element("button"), new Element("input")));

        sut.setCheckLocatorsUniqueness(true);
        sut.validate(searchRules, urls);
        verify(searchRule).extractElementsFromWebSite(urls.get(0));
    }

    @Test(expected = ValidationException.class)
    public void validateSearchRules_ValidationExceptionThrownWhenNotSupportedType() throws Exception {
        when(searchRule.getType()).thenReturn("img");

        sut.validate(searchRules, urls);
    }

    @Test(expected = ValidationException.class)
    public void validateSearchRules_ValidationExceptionThrownWhenHasNoLocator() throws Exception {
        when(searchRule.getType()).thenReturn("button");
        when(searchRule.getXpath()).thenReturn(null);

        sut.validate(searchRules, urls);
    }
}
