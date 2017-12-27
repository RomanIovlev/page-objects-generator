package com.epam.page.object.generator.util;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

/**
 * This class responsible for extracting of inner {@link Elements} from {@link Element}.
 */
public class SearchRuleExtractor {

    public SearchRuleExtractor() {
    }

    /**
     * This method returns all inner {@link Elements} according to {@link SearchRule}.
     *
     * @param element {@link Element} that we are extracting from {@link Elements}.
     * @param searchRule {@link SearchRule} is a rule that get suitable {@link Selector}.
     * @return {@link Elements} from {@link Element} according to {@link SearchRule}
     */
    public Elements extractElementsFromElement(Element element, SearchRule searchRule) {
        Selector selector = searchRule.getSelector();
        if (selector.isXpath()) {
            return Xsoup.compile(selector.getValue()).evaluate(element).getElements();
        } else if (selector.isCss()) {
            return element.select(selector.getValue());
        }
        throw new IllegalArgumentException("Selector type is unknown " + selector.toString());
    }
}