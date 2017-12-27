package com.epam.page.object.generator.model.searchrule;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.webgroup.WebElementGroup;
import com.epam.page.object.generator.model.webelement.WebElement;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * {@link SearchRule} is a rule containing certain JSON and based on which the element is searched
 * on the HTML page. {@link SearchRule} interface extends {@link Validatable} interface that is
 * responsible for verifying the correctness of the {@link SearchRule}. This is the main interface
 * that you should implement to create a new {@link SearchRule}.
 *
 * @see CommonSearchRule
 * @see ComplexSearchRule
 * @see ComplexInnerSearchRule
 * @see FormSearchRule
 * @see FormInnerSearchRule
 */
public interface SearchRule extends Validatable {

    /**
     * This method returns the {@link Selector} of particular {@link SearchRule}
     *
     * @return {@link Selector} of particular {@link SearchRule}
     */
    Selector getSelector();

    /**
     * This method wraps the JSOUP {@link Elements} in the list of {@link WebElement}
     *
     * @param elements which JSOUP parse from HTML page
     * @return list of {@link WebElement}
     */
    List<WebElement> getWebElements(Elements elements);

    /**
     * This method fills list of {@link WebElementGroup} with {@link Element} that were found by
     * certain {@link SearchRule} on the HTML page
     *
     * @param webElementGroups list of {@link WebElementGroup} from certain {@link WebPage}, where
     * we want to add new groups
     * @param elements {@link Elements} which we find from HTML page by the {@link SearchRule} and
     * we want to add them to the certain {@link WebPage} as a new {@link WebElementGroup}
     */
    void fillWebElementGroup(List<WebElementGroup> webElementGroups,
                             Elements elements);
}
