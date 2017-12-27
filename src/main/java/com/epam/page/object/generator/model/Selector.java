package com.epam.page.object.generator.model;

import com.epam.page.object.generator.model.searchrule.SearchRule;

/**
 * This class used in {@link SearchRule} to describe how to search element on web page
 */
public class Selector {

    /**
     * Defines type of selector, css or xpath
     */
    private String type;
    /**
     * The value that element must correspond to search result by described type
     */
    private String value;

    public Selector(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isXpath() {
        return type.equals("xpath");
    }

    public boolean isCss() {
        return type.equals("css");
    }

    @Override
    public String toString() {
        return "{" +
            "type='" + type + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
