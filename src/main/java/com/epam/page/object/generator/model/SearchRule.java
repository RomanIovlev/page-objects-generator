package com.epam.page.object.generator.model;

import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

public class SearchRule {

    @JsonProperty("name")
    private String requiredAttribute;

    private String title;
    private String type;
    private String css;
    private String xpath;
    private List<SearchRule> innerSearchRules;

    public SearchRule() {

    }

    public SearchRule(String type, String requiredAttribute, String title, String css,
                      String xpath, List<SearchRule> innerSearchRules) {
        this.type = type;
        this.requiredAttribute = requiredAttribute;
        this.title = title;
        this.css = css;
        this.xpath = xpath;
        this.innerSearchRules = innerSearchRules;
    }

    public List<String> getRequiredValueFromFoundElement(String url) throws IOException {
        Elements elements = extractElementsFromWebSite(url);

        return requiredAttribute.equals("text")
            ? elements.eachText()
            : elements.eachAttr(requiredAttribute);
    }

    public Elements extractElementsFromWebSite(String url) throws IOException {
        Document document = getDocument(url);

        if (css == null) {
            return Xsoup.compile(xpath).evaluate(document).getElements();
        }

        return document.select(css);
    }

    private Document getDocument(String url) throws IOException {
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

    public void setInnerSearchRules(List<SearchRule> innerSearchRules) {
        this.innerSearchRules = innerSearchRules;
    }

    @Override
    public String toString() {
        return "SearchRule{" +
            "requiredAttribute='" + requiredAttribute + '\'' +
            ", title='" + title + '\'' +
            ", type='" + type + '\'' +
            ", css='" + css + '\'' +
            ", xpath='" + xpath + '\'' +
            ", innerSearchRules=" + innerSearchRules +
            '}';
    }
}