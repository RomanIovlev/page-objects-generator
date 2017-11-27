package com.epam.page.object.generator.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SearchRuleTest {

    private SearchRule ruleWithRootTitle =
        new SearchRule(null, "text", "root", null, "//a[@class='dropdown-button']", null);
    private SearchRule ruleWithoutRootTitleWithoutUniqueness =
        new SearchRule(null, null, "list", "ul.dropdown-content", null, null);

    private SearchRule complexRuleWithOneRootWithOneUniqueness =
        new SearchRule("Dropdown", null, null, null, null,
            Lists.newArrayList(ruleWithRootTitle, ruleWithoutRootTitleWithoutUniqueness));

    @Mock
    private Element element;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRequiredValueFromFoundElement_successForComplexSearchRule() throws Exception {
        String expected = "Drop Me!";
        when(element.text()).thenReturn(expected);

        assertEquals(
            complexRuleWithOneRootWithOneUniqueness.getRequiredValueFromFoundElement(element),
            expected);
    }

    @Test
    public void getRequiredValueFromFoundElement_successForComplexInnerSearchRule()
        throws Exception {
        assertEquals(
            complexRuleWithOneRootWithOneUniqueness.getRequiredValueFromFoundElement(element),
            null);
    }
}