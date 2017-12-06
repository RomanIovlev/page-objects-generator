package com.epam.page.object.generator.utils.searchRuleGroups;

import com.epam.page.object.generator.utils.SearchRuleType;
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

    public void setName(String name) {
        this.name = name;
    }

    public List<SearchRuleType> getSearchRuleTypes() {
        return searchRuleTypes;
    }
}
