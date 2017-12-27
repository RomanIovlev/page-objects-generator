package com.epam.page.object.generator.model.webelement;

import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import org.jsoup.nodes.Element;

/**
 * Interface for elements used as parts of {@link WebPage}: <br> <ul> <li>{@link
 * CommonWebElement}</li> <li>{@link ComplexWebElement}</li> <li>{@link FormWebElement}</li> </ul>
 * Implementations of this interface wrap jsoup {@link Element} with uniqueness value from {@link
 * SearchRule}
 *
 * @see CommonWebElement
 * @see ComplexWebElement
 * @see FormWebElement
 */
public interface WebElement {

    /**
     * Returns {@link Element} which was found on the web site
     *
     * @return {@link Element} which was found on the web site
     */
    Element getElement();

    /**
     * Returns value received from webPage by 'uniqueness' attribute that was specified in {@link
     * SearchRule}
     *
     * @return value received from webPage by 'uniqueness' attribute that was specified in {@link
     * SearchRule}
     */
    String getUniquenessValue();
}
