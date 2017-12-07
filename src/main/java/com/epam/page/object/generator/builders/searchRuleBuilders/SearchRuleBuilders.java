package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.builders.searchRuleBuilders.SearchRuleBuilder;
import com.epam.page.object.generator.model.RawSearchRule;
import java.util.Map;

public class SearchRuleBuilders {

    private Map<String, SearchRuleBuilder> builders;

    public SearchRuleBuilders(
        Map<String, SearchRuleBuilder> builders) {
        this.builders = builders;
    }

    public Map<String, SearchRuleBuilder> getBuilders() {
        return builders;
    }

    public void setBuilders(Map<String, SearchRuleBuilder> builders) {
        this.builders = builders;
    }

    public SearchRuleBuilder getBuilder(RawSearchRule rawSearchRule) {
        return builders.get(rawSearchRule.getGroupName());
    }

}
