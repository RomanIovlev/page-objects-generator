package com.epam.page.object.generator.model;

import static com.epam.commons.LinqUtils.where;

import com.epam.page.object.generator.builder.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchRule {

    public String type;
    public String tag;
    public String requiredAttribute;
    public List<String> classes;
    public List<ElementAttribute> attributes;
    public List<SearchRule> innerSearchRules;


    public SearchRule(JSONObject jsonObject) {
        if (jsonObject.get("type") != null) {
            type = ((String) jsonObject.get("type")).toLowerCase();
        }
        requiredAttribute = (String) jsonObject.get("name");
        String rulesString = (String) jsonObject.get("rules");
        Pairs rules = new Pairs(rulesString.split(";"),
            r -> r.split("=")[0],
            r -> r.split("=")[1]);
        tag = rules.first(key -> key.equals("tag"));
        classes = rules.filter(
            key -> key.equals("class"));
        attributes = rules.filterAndMap(
            key -> key.equals("class"),
            pair -> new ElementAttribute(pair.key, pair.value));

        if (hasInnerRules(jsonObject)) {
            parseInnerRules(jsonObject.get("FindBy") != null ? (JSONArray) jsonObject.get("FindBy")
                : (JSONArray) jsonObject
                    .get("J" + StringUtils.firstLetterUp(type)));
        }
    }

    public List<String> extractRequiredValuesFromFoundElements(String url) throws IOException {
        return requiredAttribute.equals("text")
            ? extractElementsFromWebSite(url).eachText()
            : extractElementsFromWebSite(url).eachAttr(requiredAttribute);
    }

    private Elements extractElementsFromWebSite(String url) throws IOException {
        Elements searchResults = new Elements();
        Document document = getURLConnection(url);
        searchResults.addAll(searchElementsByTag(document));
        searchResults.retainAll(searchElementsByClasses(document));
        searchResults.retainAll(searchElementsByAttributes(document));
        return new Elements(searchResults);
    }

    public boolean classesAreEmpty() {
        return classes == null || classes.isEmpty();
    }

    public boolean attributesAreEmpty() {
        return attributes == null || attributes.isEmpty();
    }

    private void parseInnerRules(JSONArray jsonArray) {
        List<SearchRule> innerSearchRules = new ArrayList<>();

        for (Object innerSearchRule : jsonArray) {
            innerSearchRules.add(new SearchRule((JSONObject) innerSearchRule));
        }

        this.innerSearchRules = innerSearchRules;
    }

    private boolean hasInnerRules(JSONObject jsonObject) {
        if (jsonObject.get("type") != null && (jsonObject
            .get("J" + StringUtils.firstLetterUp(type)) != null
            || jsonObject.get("FindBy") != null)) {
            return true;
        } else {
            return false;
        }
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
        return !attributesAreEmpty()
            ? new Elements(where(document.getAllElements(),
            this::elementAttributesMatch))
            : document.getAllElements();
    }

    private boolean elementAttributesMatch(Element element) {
        return attributes.stream()
            .noneMatch(elementAttribute -> element.attr(elementAttribute.getAttributeName()) == null
                || !element.attr(elementAttribute.getAttributeName())
                .equals(elementAttribute.getAttributeValue()));
    }

    private String prepareCSSQuerySelector() {
        StringBuilder selector = new StringBuilder();
        classes.forEach(clazz -> selector.append(".").append(clazz));
        return selector.toString();
    }

    private Document getURLConnection(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}