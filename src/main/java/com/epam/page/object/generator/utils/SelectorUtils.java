package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.SearchRule;

public class SelectorUtils {

    public static String resultCssSelector(SearchRule searchRule, String elementsRequiredValue) {
        return String.format("%s[%s='%s']", searchRule.getCss(), searchRule.getRequiredAttribute(), elementsRequiredValue);
    }

    public static String resultXpathSelector(SearchRule searchRule, String elementsRequiredValue) {
        String xpathWithoutCloseBracket= searchRule.getXpath().replace("]", "");

        if (searchRule.getInnerSearchRules() == null) {

            if (!searchRule.getRequiredAttribute().equalsIgnoreCase("text")) {
                return xpathWithoutCloseBracket + " and @"
                    + searchRule.getRequiredAttribute() + "='" + elementsRequiredValue + "']";
            } else {
                return xpathWithoutCloseBracket + " and text()='" + elementsRequiredValue + "']";
            }
        }

        return "";
    }

}
