package com.epam.page.object.generator.util;

import java.util.List;

public class SearchRuleGroup {

    private String name;
    private List<SearchRuleType> searchRuleTypes;

    public SearchRuleGroup(String name, List<SearchRuleType> searchRuleTypes) {
        this.name = name;
        this.searchRuleTypes = searchRuleTypes;
    }

    public String getName() {
        return name;
    }

    public List<SearchRuleType> getSearchRuleTypes() {
        return searchRuleTypes;
    }
}
