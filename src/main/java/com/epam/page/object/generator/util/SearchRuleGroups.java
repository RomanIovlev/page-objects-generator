package com.epam.page.object.generator.util;

import java.util.List;

public class SearchRuleGroups {

    private List<SearchRuleGroup> ruleGroupList;

    public SearchRuleGroups(List<SearchRuleGroup> ruleGroupList) {
        this.ruleGroupList = ruleGroupList;
    }

    public SearchRuleGroup getGroupByType(SearchRuleType type) {
        return ruleGroupList.stream()
            .filter(searchRuleGroup -> searchRuleGroup.getSearchRuleTypes().contains(type))
            .findFirst()
            .orElse(null);
    }
}
