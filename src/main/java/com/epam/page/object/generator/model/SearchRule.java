package com.epam.page.object.generator.model;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import static com.epam.commons.LinqUtils.*;
import static java.util.Arrays.asList;

public class SearchRule {

    public String type;
    public String tag;
    public String requiredAttribute;
    public List<String> classes;
    public List<ElementAttribute> attributes;

    public SearchRule(JSONObject jsonObject) {
        type = ((String) jsonObject.get("type")).toLowerCase();
        requiredAttribute = (String) jsonObject.get("name");
        String rulesString = (String) jsonObject.get("rules");
        Pairs rules = new Pairs(rulesString.split(";"),
                r -> r.split("=")[0],
                r -> r.split("=")[1]);
        tag = rules.first(key -> key.equals("tag"));
        classes = rules.filter(
                key -> key.equals("class"));
        attributes = rules.filterAndMap(
            key -> !(key.equals("class")),
            pair -> new ElementAttribute(pair.key, pair.value));
    }
    public List<String> getElements(String url) throws IOException {
        return requiredAttribute.equals("text")
            ? extractElementsFromWebSite(url).eachText()
            : extractElementsFromWebSite(url).eachAttr(requiredAttribute);
    }

	public Elements extractElementsFromWebSite(String url) throws IOException {
        Elements searchResults = new Elements();
        Document document = getURLConnection(url);
        searchResults.addAll(searchElementsByTag(document));
        searchResults.retainAll(searchElementsByClasses(document));
        searchResults.retainAll(searchElementsByAttributes(document));
        return new Elements(searchResults);
    }

    private Elements searchElementsByTag(Document document) {
        return tag != null
            ? document.select(tag)
            : document.getAllElements();
    }

    private Elements searchElementsByClasses(Document document) {
        return !classesAreEmpty()
                ? document.select(prepareCSSQuerySelector())
                : document.getAllElements();
    }

    private Elements searchElementsByAttributes(Document document) {
        return attributesAreEmpty()
            ? new Elements(where(document.getAllElements(),
                this::elementAttributesMatch))
            : document.getAllElements();
    }

    private boolean elementAttributesMatch(Element element) {
        return attributes.stream().noneMatch(elementAttribute -> element.attr(elementAttribute.getAttributeName()) == null
                || !element.attr(elementAttribute.getAttributeName()).equals(elementAttribute.getAttributeValue()));
    }

    private String prepareCSSQuerySelector() {
        StringBuilder selector = new StringBuilder();
        classes.forEach(clazz -> selector.append(".").append(clazz));
        return selector.toString();
    }

    private Document getURLConnection(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public boolean classesAreEmpty() {
        return classes == null || classes.isEmpty();
    }

    public boolean attributesAreEmpty() {
        return attributes == null || attributes.isEmpty();
    }

}