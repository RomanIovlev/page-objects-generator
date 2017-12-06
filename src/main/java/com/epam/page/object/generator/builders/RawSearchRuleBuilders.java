package com.epam.page.object.generator.builders;

import com.epam.page.object.generator.model.RawSearchRule;
import java.util.Map;

public class RawSearchRuleBuilders {

    private Map<String, RawSearchRuleBuilder> builders;

    public RawSearchRuleBuilders(
        Map<String, RawSearchRuleBuilder> builders) {
        this.builders = builders;
    }

    public Map<String, RawSearchRuleBuilder> getBuilders() {
        return builders;
    }

    public void setBuilders(Map<String, RawSearchRuleBuilder> builders) {
        this.builders = builders;
    }

    public RawSearchRuleBuilder getBuilder(RawSearchRule rawSearchRule) {
        return builders.get(rawSearchRule.getGroupName());
    }

}
