package com.epam.page.object.generator.model;

import org.jsoup.nodes.Element;

public class WebElement {

    private Element element;
    private String uniquenessValue;

    public WebElement(Element element, String uniquenessValue) {
        this.element = element;
        this.uniquenessValue = uniquenessValue;
    }

    public Element getElement() {
        return element;
    }

    public String getUniquenessValue() {
        return uniquenessValue;
    }
}
