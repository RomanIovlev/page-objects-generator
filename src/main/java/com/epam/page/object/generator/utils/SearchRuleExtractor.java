package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

public class SearchRuleExtractor {

    public static Elements extractElementsFromElement(Element element, SearchRule searchRule) {
        Selector selector = searchRule.getSelector();
        if(selector.isXpath()){
            return Xsoup.compile(selector.getValue()).evaluate(element).getElements();
        }
        return element.select(selector.getValue());
    }

    public static Element extractElement(Element element, SearchRule searchRule) {
        Selector selector = searchRule.getSelector();
        Elements elements;
        if(selector.isXpath()){
            elements = Xsoup.compile(selector.getValue()).evaluate(element).getElements();
        } else if (selector.isCss()){
            elements = element.select(selector.getValue());
        } else {
            throw new NullPointerException("wrong selector type");
        }

        if (elements.size() != 1) throw new RuntimeException("wrong elements number");

        return elements.first();
    }

}
