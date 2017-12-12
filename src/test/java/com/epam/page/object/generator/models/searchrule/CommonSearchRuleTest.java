package com.epam.page.object.generator.models.searchrule;

import static org.junit.Assert.assertEquals;

import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.testDataBuilders.searchRuleDataTestBuilders.CommonSearchRuleTestDataBuilder;
import org.junit.Test;

public class CommonSearchRuleTest {

    private CommonSearchRule commonSearchRule;

    @Test
    public void getTransformedSelector_UniquenessValue_SelectorXpath() {
        commonSearchRule = CommonSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessValue_SelectorXpath();

        Selector transformedSelector = commonSearchRule.getTransformedSelector();
        assertEquals("css", transformedSelector.getType());
        assertEquals("input[type=submit]", transformedSelector.getValue());
    }

    @Test
    public void getTransformedSelector_UniquenessText_SelectorXpath() {
        commonSearchRule = CommonSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessText_SelectorXpath();

        Selector transformedSelector = commonSearchRule.getTransformedSelector();
        assertEquals("xpath", transformedSelector.getType());
        assertEquals("//input[@type='submit']", transformedSelector.getValue());
    }

    @Test
    public void getTransformedSelector_UniquenessValue_SelectorCss() {
        commonSearchRule = CommonSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessValue_SelectorCss();

        Selector transformedSelector = commonSearchRule.getTransformedSelector();
        assertEquals("css", transformedSelector.getType());
        assertEquals("input[type=submit]", transformedSelector.getValue());
    }

    @Test
    public void getTransformedSelector_UniquenessText_SelectorCss() {
        commonSearchRule = CommonSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessText_SelectorCss();

        Selector transformedSelector = commonSearchRule.getTransformedSelector();
        assertEquals("css", transformedSelector.getType());
        assertEquals("input[type=submit]", transformedSelector.getValue());
    }
}