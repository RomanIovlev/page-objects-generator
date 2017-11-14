package com.epam.page.object.generator.containers;

import com.epam.page.object.generator.model.SearchRule;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class SearchRulesContainer {

    @JsonProperty("elements")
    private List<SearchRule> searchRules;

    public List<SearchRule> getSearchRules() {
        return searchRules;
    }

    public void setSearchRules(List<SearchRule> searchRules) {
        this.searchRules = searchRules;
    }

    @Override
    public String toString() {
        return "Elements{" +
            "searchRules=" + searchRules +
            '}';
    }

}