package com.epam.page.object.generator.model.webelement;

import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import org.jsoup.nodes.Element;

/**
 * This class represents {@link Element} which was found in the web site by the {@link
 * CommonSearchRule} and its 'uniqueness' value from this element
 */
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
