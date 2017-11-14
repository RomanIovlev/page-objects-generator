package com.epam.page.object.generator.model;

import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;
import java.util.List;

public class SearchRule {

    private String uniqueness;
    private String title;
    private String type;
    private String css;
    private String xpath;
    private List<SearchRule> innerSearchRules;

    public SearchRule() {

    }

    public SearchRule(String type, String uniqueness, String title, String css,
                      String xpath, List<SearchRule> innerSearchRules) {
        this.type = type == null ? null : type.toLowerCase();
        this.uniqueness = uniqueness;
        this.title = title;
        this.css = css;
        this.xpath = xpath;
        this.innerSearchRules = innerSearchRules;
    }

    public List<String> getRequiredValueFromFoundElement(String url) throws IOException {
        Elements elements = extractElementsFromWebSite(url);

        if (uniqueness == null) {
            //  TODO: Find out how to name field for found complex element
            return Lists.newArrayList(type);
        }

        return uniqueness.equals("text")
            ? elements.eachText()
            : elements.eachAttr(uniqueness);
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
        this.type = type == null ? null : type.toLowerCase();
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(String uniqueness) {
        this.uniqueness = uniqueness;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SearchRule{" +
            "uniqueness='" + uniqueness + '\'' +
            ", title='" + title + '\'' +
            ", type='" + type + '\'' +
            ", css='" + css + '\'' +
            ", xpath='" + xpath + '\'' +
            ", innerSearchRules=" + innerSearchRules +
            '}';
    }
}