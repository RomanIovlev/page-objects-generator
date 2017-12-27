package com.epam.page.object.generator.model.webelement;

import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import org.jsoup.nodes.Element;

/**
 * This class represents {@link Element} which was found in the web site by the {@link
 * ComplexInnerSearchRule} and its 'uniqueness' value from this element
 */
public class ComplexWebElement implements WebElement {

    private Element element;
    private String uniquenessValue;
    /**
     * Search rule required to form list of inner elements
     */
    private ComplexInnerSearchRule searchRule;

    public ComplexWebElement(Element element, String uniquenessValue,
                             ComplexInnerSearchRule searchRule) {
        this.element = element;
        this.uniquenessValue = uniquenessValue;
        this.searchRule = searchRule;
    }

    public ComplexInnerSearchRule getSearchRule() {
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
