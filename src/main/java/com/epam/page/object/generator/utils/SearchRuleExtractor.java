package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

public class SearchRuleExtractor {

    public Elements extractElementsFromElement(Element element, SearchRule searchRule) {
        Selector selector = searchRule.getSelector();
        if (selector.isXpath()) {
            return Xsoup.compile(selector.getValue()).evaluate(element).getElements();
        }
        return element.select(selector.getValue());
    }
}
