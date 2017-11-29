package com.epam.page.object.generator.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;

public class WebPage {


    private final URI uri;
    private Document document;
    private List<SearchRule> validSearchRulesOfCurrentWebPage;
    private List<SearchRule> invalidSearchRulesOfCurrentWebPage;


    protected WebPage(URI  uri, Document document) {
        this.validSearchRulesOfCurrentWebPage = new ArrayList<>();
        this.invalidSearchRulesOfCurrentWebPage = new ArrayList<>();
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

    public List<SearchRule> getValidSearchRules() {
        return validSearchRulesOfCurrentWebPage;
    }

    public List<SearchRule> getInvalidSearchRules() {
        return invalidSearchRulesOfCurrentWebPage;
    }

    public void addSearchRulesForCurrentWebPage(List<SearchRule> searchRules) {
        for (SearchRule searchRule : searchRules) {
            if (searchRule.extractElementsFromElement(document).size() == 0) {
                invalidSearchRulesOfCurrentWebPage.add(searchRule);
            } else {
                validSearchRulesOfCurrentWebPage.add(searchRule);
            }
        }


    }


}
