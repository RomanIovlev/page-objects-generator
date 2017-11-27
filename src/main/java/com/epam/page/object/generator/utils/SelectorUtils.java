package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.SearchRule;

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
            return searchRule.getCss();
        }

        return String.format("%s[%s='%s']", searchRule.getCss(), searchRule.getUniqueness(),
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

}
