package com.epam.page.object.generator.model;

import static java.util.Arrays.asList;

import com.epam.page.object.generator.utils.SearchRuleTypeGroups;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

public class SearchRule {

    public String tag;
    public String requiredAttribute;
    public List<String> classes;
    public Pairs attributes;
    private String uniqueness;
    private String title;
    private String type;
    private String css;
    private String xpath;
    private String section;
    private List<SearchRule> innerSearchRules;

    public SearchRule() {
    }

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

    public String getRequiredValueFromFoundElement(Element element) throws IOException {

        if (uniqueness == null && this.getInnerSearchRules() == null) {
            return null;
        }

        if (SearchRuleTypeGroups.isComplexType(this)) {
            String uniquenessForComplexSearchRule = getRootInnerRule().get().getUniqueness();
            return getValueFromUniquenessAttribute(element, uniquenessForComplexSearchRule);
        }

        return getValueFromUniquenessAttribute(element, uniqueness);
    }

    public List<String> getRequiredValueFromFoundElement(Elements elements) throws IOException {

        if (uniqueness == null && this.getInnerSearchRules() == null) {
            return null;
        }

        if (SearchRuleTypeGroups.isComplexType(this)) {
            String uniquenessForComplexSearchRule = getRootInnerRule().get().getUniqueness();
            return getValueFromUniquenessAttribute(elements, uniquenessForComplexSearchRule);
        }

        return getValueFromUniquenessAttribute(elements, uniqueness);

    }

    private String getValueFromUniquenessAttribute(Element element,
                                                   String uniqueness) {
        return uniqueness.equals("text")
            ? element.text()
            : element.attr(uniqueness);
    }

    private List<String> getValueFromUniquenessAttribute(Elements elements,
                                                         String uniqueness) {
        return uniqueness.equals("text")
            ? elements.eachText()
            : elements.eachAttr(uniqueness);
    }

    public Elements extractElementsFromWebSite(String url) throws IOException {
        Document document = getDocument(url);

        String correctXpath = getXpath();
        String correctCss = getCss();

        // Correct xpath or css for Complex element
        if (SearchRuleTypeGroups.isComplexType(this)) {
            correctCss = extractCssFromRootInnerRule();
            correctXpath = extractXpathFromRootInnerRule();
        }

        if (correctCss == null) {
            return Xsoup.compile(correctXpath).evaluate(document).getElements();
        }

        return document.select(correctCss);
    }

    private String extractCssFromRootInnerRule() {
        Optional<SearchRule> rootElement = getRootInnerRule();

        return rootElement.isPresent() ? rootElement.get().getCss() : null;
    }

    private String extractXpathFromRootInnerRule() {
        Optional<SearchRule> rootElement = getRootInnerRule();

        return rootElement.isPresent() ? rootElement.get().getXpath() : null;
    }

    /**
     * Get 'root' innerSearchRule from complexSearchRule.
     *
     * @return {@link Optional} about existence 'root' innerSearchRule.
     */
    public Optional<SearchRule> getRootInnerRule() {
        return innerSearchRules.stream()
            .filter(innerSearchRule -> innerSearchRule.getTitle().equals("root"))
            .findFirst();
    }

    public Elements extractElementsFromElement(Element element) {
        if (css == null) {
            return Xsoup.compile(xpath).evaluate(element).getElements();
        }

        return element.select(css);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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