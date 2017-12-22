package com.epam.page.object.generator.util;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

public class SearchRuleExtractor {

    public SearchRuleExtractor() {
    }

    public Elements extractElementsFromElement(Element element, SearchRule searchRule) {
        Selector selector = searchRule.getSelector();
        if (selector.isXpath()) {
            return Xsoup.compile(selector.getValue()).evaluate(element).getElements();
        }
        else if(selector.isCss()) {
            return element.select(selector.getValue());
        }
        throw new IllegalArgumentException("Selector type is unknown " + selector.toString());
    }
}
