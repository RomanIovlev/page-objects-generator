package com.epam.page.object.generator.utils;

import static org.junit.Assert.*;

import com.epam.page.object.generator.model.Selector;
import org.junit.Test;

public class SelectorUtilsTest {

    private Selector xpathSelector = new Selector("xpath", "//button[@class='dropbtn']");
    private Selector cssSelector = new Selector("css", "ul.dropdown-content");

    @Test
    public void resultCssSelector_WithUniquenessAttributeTest() {
        String actual = SelectorUtils.resultCssSelector(cssSelector, "test string", "text");
        assertEquals(cssSelector.getValue() + "[text='test string']", actual);
    }

    @Test
    public void resultCssSelector_WithoutUniquenessAttributeTest() {
        String actual = SelectorUtils.resultCssSelector(cssSelector, null, null);
        assertEquals(cssSelector.getValue(), actual);
    }

    @Test
    public void resultXpathSelector_WithTextUniquenessAttributeTest() {
        String actual = SelectorUtils.resultXpathSelector(xpathSelector, "test string", "text");
        assertEquals("//button[@class='dropbtn' and text()='test string']", actual);
    }

    @Test
    public void resultXpathSelector_WithValueUniquenessAttributeTest() {
        String actual = SelectorUtils.resultXpathSelector(xpathSelector, "test string", "value");
        assertEquals("//button[@class='dropbtn' and @value='test string']", actual);
    }

    @Test
    public void resultXpathSelector_WithoutUniquenessAttributeTest(){
        String actual = SelectorUtils.resultXpathSelector(xpathSelector, null, null);
        assertEquals("//button[@class='dropbtn']", actual);
    }
}