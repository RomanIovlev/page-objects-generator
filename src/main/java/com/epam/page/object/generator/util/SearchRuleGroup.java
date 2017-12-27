package com.epam.page.object.generator.util;

import java.util.List;
import com.epam.page.object.generator.model.searchrule.SearchRule;

/**
 * An instance of this class {@link SearchRuleGroup} contain information about all possible types of
 * {@link SearchRule}
 */

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

    /**
     * Get information about all possible types of {@link SearchRule}
     *
     * @return List of {@link SearchRuleType}
     */
    public List<SearchRuleType> getSearchRuleTypes() {
        return searchRuleTypes;
    }
}
