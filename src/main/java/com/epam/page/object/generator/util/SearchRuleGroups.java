package com.epam.page.object.generator.util;

import com.epam.page.object.generator.model.searchrule.SearchRule;
import java.util.List;

/**
 * An instance of this class {@link SearchRuleGroups} contain information about all possible {@link
 * SearchRuleGroup}
 */
public class SearchRuleGroups {

    private List<SearchRuleGroup> ruleGroupList;

    public SearchRuleGroups(List<SearchRuleGroup> ruleGroupList) {
        this.ruleGroupList = ruleGroupList;
    }

    /**
     * Find required group by uses type of {@link SearchRule}
     *
     * @param type {@link SearchRuleType} which we search for the group
     * @return return founded {@link SearchRuleGroup} that includes this {@link SearchRuleType}
     */
    public SearchRuleGroup getGroupByType(SearchRuleType type) {
        return ruleGroupList.stream()
            .filter(searchRuleGroup -> searchRuleGroup.getSearchRuleTypes().contains(type))
            .findFirst()
            .orElse(null);
    }
}
