package com.epam.page.object.generator.model;

import static org.junit.Assert.*;

import com.google.common.collect.Lists;
import java.io.IOException;
import org.junit.Test;

public class SearchRuleTest {

    private SearchRule ruleWithRootTitle =
        new SearchRule(null, "text", "root", null, "//a[@class='dropdown-button']", null);
    private SearchRule ruleWithoutRootTitleWithoutUniqueness =
        new SearchRule(null, null, "list", "ul.dropdown-content", null, null);

    private SearchRule complexRuleWithOneRootWithOneUniqueness =
        new SearchRule("Dropdown", null, null, null, null,
            Lists.newArrayList(ruleWithRootTitle, ruleWithoutRootTitleWithoutUniqueness));

    @Test
    public void getRequiredValueFromFoundElement_successForComplexSearchRule() throws Exception {
        String expected = "Drop Me!";

        assertEquals(complexRuleWithOneRootWithOneUniqueness
                .getRequiredValueFromFoundElement("http://materializecss.com/dropdown.html"),
            Lists.newArrayList(expected));
    }

    @Test
    public void getRequiredValueFromFoundElement_successForComplexInnerSearchRule()
        throws Exception {

        assertEquals(ruleWithoutRootTitleWithoutUniqueness
            .getRequiredValueFromFoundElement("http://materializecss.com/dropdown.html"), null);
    }
}