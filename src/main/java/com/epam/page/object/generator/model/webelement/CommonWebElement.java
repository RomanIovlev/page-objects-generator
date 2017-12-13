package com.epam.page.object.generator.model.webelement;

import org.jsoup.nodes.Element;

public class CommonWebElement implements WebElement {

    private Element element;
    private String uniquenessValue;

    public CommonWebElement(Element element, String uniquenessValue) {
        this.element = element;
        this.uniquenessValue = uniquenessValue;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String getUniquenessValue() {
        return uniquenessValue;
    }

    @Override
    public String toString() {
        return "WebElement{" + element + '}';
    }
}
