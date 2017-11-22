package com.epam.page.object.generator.model;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;

import java.util.Optional;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

import static java.util.Arrays.asList;

public class SearchRule {

    private String uniqueness;
    private String title;
    private String type;
    private String css;
    private String xpath;
    private List<SearchRule> innerSearchRules;

    public SearchRule() {
    }

    public String tag;
    public String requiredAttribute;
    public List<String> classes;
    public Pairs attributes;

    public SearchRule(JSONObject jsonObject) {
        type = ((String) jsonObject.get("type")).toLowerCase();
        requiredAttribute = (String) jsonObject.get("name");
        String rulesString = (String) jsonObject.get("rules");
        Pairs rules = new Pairs(asList(rulesString.split(";")),
            r -> r.split("=")[0],
            r -> r.split("=")[1]);
        tag = rules.first(key -> key.equals("tag"));
        classes = rules.filter(
            key -> key.equals("class"));
        attributes = rules;
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

        if (uniqueness == null && this.getInnerSearchRules() == null) {
            return null;
        }

        if (uniqueness == null) {

            if (this.getRootInnerRule().isPresent()) {
                String uniqueness = this.getRootInnerRule().get().getUniqueness();
                return getValueFromUniquenessAttribute(elements, uniqueness);
            } else {
                return Lists.newArrayList(type);
            }
        }

        return getValueFromUniquenessAttribute(elements, uniqueness);
    }

    private List<String> getValueFromUniquenessAttribute(Elements elements,
                                                         String uniqueness) {
        return uniqueness.equals("text")
            ? elements.eachText()
            : elements.eachAttr(uniqueness);
    }

    public Elements extractElementsFromWebSite(String url) throws IOException {
        Document document = getDocument(url);

        // Correct xpath or css for Complex element
        String correctXpath = returnXpathFromSearchRule();
        String correctCss = returnCssFromSearchRule();

        if (correctCss == null) {
            return Xsoup.compile(correctXpath).evaluate(document).getElements();
        }

        return document.select(correctCss);
    }

    private String returnCssFromSearchRule() {
        return innerSearchRules == null ? getCss() : extractCssFromRootInnerRule();
    }

    private String returnXpathFromSearchRule() {
        return innerSearchRules == null ? getXpath() : extractXpathFromRootInnerRule();
    }

    private String extractCssFromRootInnerRule() {
        Optional<SearchRule> rootElement = getRootInnerRule();

        return rootElement.isPresent() ? rootElement.get().getCss() : null;
    }

    private String extractXpathFromRootInnerRule() {
        Optional<SearchRule> rootElement = getRootInnerRule();

        return rootElement.isPresent() ? rootElement.get().getXpath() : null;
    }

    public Optional<SearchRule> getRootInnerRule() {
        return innerSearchRules.stream()
            .filter(innerSearchRule -> innerSearchRule.getTitle().equals("root"))
            .findFirst();
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

    private boolean elementAttributesMatch(Element element) {
        return attributes.stream()
            .noneMatch(elementAttribute -> element.attr(elementAttribute.getName()) == null
                || !element.attr(elementAttribute.getName()).equals(elementAttribute.getValue()));
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