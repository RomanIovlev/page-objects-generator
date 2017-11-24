package com.epam.page.object.generator.model;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class WebPage {

    private final String url;
    private Document document;
    private List<SearchRule> validSearchRulesOfCurrentWebPage;
    private List<SearchRule> invalidSearchRulesOfCurrentWebPage;

    public WebPage(String url, Document document) {
        this.url = url;
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public String getTitle() {
        return document.title();
    }

    public String getUrl() {
        return url;
    }

    public List<SearchRule> getValidSearchRulesOfCurrentWebPage() {
        return validSearchRulesOfCurrentWebPage;
    }
    public List<SearchRule> getInvalidSearchRulesOfCurrentWebPage() {
        return invalidSearchRulesOfCurrentWebPage;
    }

    public void addSearchRulesForCurrentWebPage(List<SearchRule> searchRules) {
        validSearchRulesOfCurrentWebPage = new ArrayList<>();
        invalidSearchRulesOfCurrentWebPage = new ArrayList<>();
        for (SearchRule searchRule:searchRules)
            {
              if(searchRule.extractElementsFromElement(document).size() == 0){
                  invalidSearchRulesOfCurrentWebPage.add(searchRule);
              }
              else{
                  validSearchRulesOfCurrentWebPage.add(searchRule);
              }
            }


    }


}
