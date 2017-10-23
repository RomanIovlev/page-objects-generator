package com.epam.page.object.generator.model;

import com.epam.page.object.generator.builder.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SearchRule {

    private String type;
    private String requiredAttribute;
    private String css;
    private String xpath;
    private List<SearchRule> innerSearchRules;


    public SearchRule(JSONObject jsonObject) {
        if (jsonObject.get("type") != null) {
            type = ((String) jsonObject.get("type")).toLowerCase();
        }
        requiredAttribute = (String) jsonObject.get("name");
        JSONObject rulesString = (JSONObject) jsonObject.get("rules");

        css = (String) rulesString.get("css");
        if (css == null) {
            xpath = (String) rulesString.get("xpath");
            if (xpath == null) {
//                throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN, rulesString);
            }
        }


        if (hasInnerRules(jsonObject)) {
            parseInnerRules(jsonObject.get("FindBy") != null ? (JSONArray) jsonObject.get("FindBy")
                : (JSONArray) jsonObject
                    .get("J" + StringUtils.firstLetterUp(type)));
        }
    }

    public String getType() {
        return type;
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

    private Document getURLConnection(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}