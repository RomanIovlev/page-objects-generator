package com.epam.page.object.generator.builders;


import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;

public class ComplexInnerSearchRuleBuilder extends RawSearchRuleBuilder {

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule) {

        String title = rawSearchRule.getValue("title");

        String uniqueness = null;
        if(title.equals("root")){
            uniqueness = rawSearchRule.getValue("uniqueness");
        }

        Selector selector = rawSearchRule.getSelector();

        return new ComplexInnerSearchRule(uniqueness, title, selector);
    }
}
