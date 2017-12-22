package com.epam.page.object.generator.util;

import com.epam.page.object.generator.model.Selector;

public class SelectorUtils {

    /**
     * Generate string contains css selector for SearchRule.
     *
     * @param selector {@link Selector} css selector
     * @param requiredValue value received from webPage by 'uniqueness' attribute
     * @param uniquenessAttribute name of the 'uniqueness' attribute
     * @return css selector
     */
    public String resultCssSelector(Selector selector, String requiredValue,
                                    String uniquenessAttribute) {
        if (requiredValue == null) {
            return selector.getValue();
        }

        return String
            .format("%s[%s='%s']", selector.getValue(), uniquenessAttribute, requiredValue);
    }

    /**
     * Generate string contains xpath selector for SearchRule.
     *
     * @param selector {@link Selector} xpath selector
     * @param requiredValue value received from webPage by 'uniqueness' attribute
     * @param uniquenessAttribute name of the 'uniqueness' attribute
     * @return xpath selector
     */
    public String resultXpathSelector(Selector selector, String requiredValue,
                                      String uniquenessAttribute) {
        String xpathWithoutCloseBracket = selector.getValue().replace("]", "");

        if (requiredValue == null) {
            return xpathWithoutCloseBracket + "]";
        }

        if (uniquenessAttribute.equalsIgnoreCase("text")) {
            return xpathWithoutCloseBracket + " and text()='" + requiredValue + "']";
        } else {
            return xpathWithoutCloseBracket + " and @"
                + uniquenessAttribute + "='" + requiredValue + "']";
        }
    }
}