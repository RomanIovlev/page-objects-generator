package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.builders.RawSearchRuleBuilder;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;

public class CommonSearchRuleBuilder extends RawSearchRuleBuilder {

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule) {

        String uniqueness = rawSearchRule.getValue("uniqueness");
        SearchRuleType type = rawSearchRule.getType();
        Selector selector = rawSearchRule.getSelector();

        return new CommonSearchRule(uniqueness, type, selector);
    }
}
