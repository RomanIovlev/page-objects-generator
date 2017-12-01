package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import org.jsoup.nodes.Element;

public class SelectorUtils {

    /**
     * Generate string contains css selector for SearchRule.
     *
     * @param searchRule {@link SearchRule} with css selector
     * @param elementsRequiredValue value received from webPage by 'uniqueness' attribute
     * @return css selector
     */
    public static String resultCssSelector(SearchRule searchRule, String elementsRequiredValue) {
        if (elementsRequiredValue == null) {
            return searchRule.getSelector().getValue();
        }

        return String.format("%s[%s='%s']", searchRule.getSelector().getValue(), searchRule.getUniqueness(),
            elementsRequiredValue);
    }

    /**
     * Generate string contains xpath selector for SearchRule.
     *
     * @param searchRule {@link SearchRule} with xpath selector
     * @param elementsRequiredValue value received from webPage by 'uniqueness' attribute
     * @return xpath selector
     */
    public static String resultXpathSelector(SearchRule searchRule, String elementsRequiredValue) {
        String xpathWithoutCloseBracket = searchRule.getXpath().replace("]", "");

        if (elementsRequiredValue == null) {
            return xpathWithoutCloseBracket + "]";
        }

        if (searchRule.getInnerSearchRules() == null) {

            if (!searchRule.getUniqueness().equalsIgnoreCase("text")) {
                return xpathWithoutCloseBracket + " and @"
                    + searchRule.getUniqueness() + "='" + elementsRequiredValue + "']";
            } else {
                return xpathWithoutCloseBracket + " and text()='" + elementsRequiredValue + "']";
            }
        }

        return "";
    }

    public static Selector resultSelector(Selector selector, Element element){
        if()

        String selectorValueWithoutClosedBracket = selector.getValue();


        if(selector.isXpath()){
            selectorValueWithoutClosedBracket = selector.getValue().replace("]", "");
        }






        if (!searchRule.getUniqueness().equalsIgnoreCase("text")) {

            if (searchRule.getSelector().isXpath()) {
                XpathToCssTransformation.getCssSelector(searchRule);
            }




        }
}
