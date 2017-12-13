package com.epam.page.object.generator.model.webelement;

import com.epam.page.object.generator.model.searchrule.FormInnerSearchRule;
import org.jsoup.nodes.Element;

public class FormWebElement implements WebElement {

    private Element element;
    private String uniquenessValue;
    private FormInnerSearchRule searchRule;

    public FormWebElement(Element element, String uniquenessValue,
                          FormInnerSearchRule searchRule) {
        this.element = element;
        this.uniquenessValue = uniquenessValue;
        this.searchRule = searchRule;
    }

    public FormInnerSearchRule getSearchRule() {
        return searchRule;
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
