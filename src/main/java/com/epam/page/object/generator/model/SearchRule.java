package com.epam.page.object.generator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SearchRule {

    public String type;

    @JsonProperty("name")
    public String requiredAttribute;

    public String css;
    public String xpath;
    public List<SearchRule> innerSearchRules;

    public SearchRule() { }

    public SearchRule(String type, String requiredAttribute, String css, String xpath,
        List<SearchRule> innerSearchRules) {
        this.type = type;
        this.requiredAttribute = requiredAttribute;
        this.css = css;
        this.xpath = xpath;
        this.innerSearchRules = innerSearchRules;
    }

    public List<String> extractRequiredValuesFromFoundElements(String url) throws IOException {
        return requiredAttribute.equals("text")
            ? extractElementsFromWebSite(url).eachText()
            : extractElementsFromWebSite(url).eachAttr(requiredAttribute);
    }

    private Elements extractElementsFromWebSite(String url) throws IOException {
        Elements searchResults = new Elements();
        Document document = getURLConnection(url);
        if (css == null) {
//       TODO get elements by xpath
        } else {
            searchResults.addAll(document.select(css));
        }
        return new Elements(searchResults);
    }

    private boolean hasInnerRules(JSONObject jsonObject) {

        return !innerSearchRules.isEmpty();
    }

    private Document getURLConnection(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequiredAttribute() {
        return requiredAttribute;
    }

    public void setRequiredAttribute(String requiredAttribute) {
        this.requiredAttribute = requiredAttribute;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public List<SearchRule> getInnerSearchRules() {
        return innerSearchRules;
    }

    public void setInnerSearchRules(
        List<SearchRule> innerSearchRules) {
        this.innerSearchRules = innerSearchRules;
    }

    @Override
    public String toString() {
        return "SearchRule{" +
            "type='" + type + '\'' +
            ", requiredAttribute='" + requiredAttribute + '\'' +
            ", css='" + css + '\'' +
            ", xpath='" + xpath + '\'' +
            ", innerSearchRules=" + innerSearchRules +
            '}';
    }
}