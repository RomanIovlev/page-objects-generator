package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SelectorUtilsTest {

    private SearchRule searchRule;
    private String elementsRequiredValue;

    @Before
    public void setUp() {
        searchRule = new CommonSearchRule("name", SearchRuleType.BUTTON, new Selector("css", "//input[@type='submit']"));
        elementsRequiredValue = "button";
    }

//    @Test
//    public void resultCssSelector_successWithoutElementsRequiredValue() throws Exception {
//        String expected = "css";
//
//        Assert.assertEquals(expected,
//            SelectorUtils.resultCssSelector(searchRule, null));
//    }
//
//    @Test
//    public void resultXpathSelector_successWithoutElementsRequiredValue() throws Exception {
//        String expected = "//input[@type='submit']";
//
//        Assert.assertEquals(expected,
//            SelectorUtils.resultXpathSelector(searchRule, null));
//    }
//
//    @Test
//    public void resultCssSelector_success() throws Exception {
//        String expected = "css[name='button']";
//
//        Assert.assertEquals(expected,
//            SelectorUtils.resultCssSelector(searchRule, elementsRequiredValue));
//
//    }
//
//    @Test
//    public void resultXpathSelector_withoutText() throws Exception {
//        String expected = "//input[@type='submit' and @name='button']";
//
//        Assert.assertEquals(expected,
//            SelectorUtils.resultXpathSelector(searchRule, elementsRequiredValue));
//    }
//
//    @Test
//    public void resultXpathSelector_withText() throws Exception {
//        searchRule.setUniqueness("text");
//        String expected = "//input[@type='submit' and text()='button']";
//
//        Assert.assertEquals(expected,
//            SelectorUtils.resultXpathSelector(searchRule, elementsRequiredValue));
//    }

}