package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.builders.RawSearchRuleBuilder;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleType;

public class FormInnerSearchRuleBuilder extends RawSearchRuleBuilder {

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule) {

        SearchRuleType type = rawSearchRule.getType();
        String uniqueness = rawSearchRule.getValue("uniqueness");
        Selector selector = rawSearchRule.getSelector();

        return new FormInnerSearchRule(uniqueness, type, selector);
    }
}
