package com.epam.page.object.generator.model.searchrule;

import static org.junit.Assert.assertEquals;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.databuilder.searchrule.CommonSearchRuleTestDataBuilder;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import org.junit.Test;
import org.openqa.selenium.support.FindBy;

public class CommonSearchRuleTest {

    private XpathToCssTransformer xpathToCssTransformer = new XpathToCssTransformer();
    private SelectorUtils selectorUtils = new SelectorUtils();

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

    @Test
    public void getTransformedSelector_XpathToCssTransformerException() {
        commonSearchRule = new CommonSearchRule("value", SearchRuleType.BUTTON,
            new Selector("xpath", "Invalid_XPATH"),
            new ClassAndAnnotationPair(Button.class, FindBy.class), xpathToCssTransformer,
            selectorUtils);

        Selector transformedSelector = commonSearchRule.getTransformedSelector();
        assertEquals("xpath", transformedSelector.getType());
        assertEquals("Invalid_XPATH", transformedSelector.getValue());
    }
}