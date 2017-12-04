package com.epam.page.object.generator.model;

import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.validators.searchRuleJsonValidators.ValidatorVisitor;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebPage {

    private final URI uri;
    private Document document;
    private List<WebElementGroup> webElementGroups;

    protected WebPage(URI uri, Document document) {
        this.webElementGroups = new ArrayList<>();
        this.uri = uri;
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public String getTitle() {
        return document.title();
    }

    public URI getUri() {
        return uri;
    }

    public String getUrlWithoutDomain() {
        return uri.getPath();
    }

    public String getDomainName() {
        return uri.getHost();
    }

    public List<WebElementGroup> getWebElementGroups() {
        return webElementGroups;
    }

    public void addSearchRules(List<SearchRule> searchRules) {
        for (SearchRule searchRule : searchRules) {
            Elements elements = extractElements(searchRule);
            if (elements.size() != 0) {
                this.webElementGroups.add(new WebElementGroup(searchRule, searchRule.getWebElements(elements)));
            }
        }
    }

    public Elements extractElements(SearchRule searchRule) {
        return SearchRuleExtractor.extractElementsFromElement(document, searchRule);
    }

    public Element extractElement(SearchRule searchRule) {
        return SearchRuleExtractor.extractElement(document, searchRule);
    }
}
