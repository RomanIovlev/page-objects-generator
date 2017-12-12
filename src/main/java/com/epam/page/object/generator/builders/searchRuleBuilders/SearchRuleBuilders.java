package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.searchRules.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import java.util.Map;

public class SearchRuleBuilders {

    private Map<String, SearchRuleBuilder> builders;

    public SearchRuleBuilders(Map<String, SearchRuleBuilder> builders) {
        this.builders = builders;
    }

    public SearchRule buildSearchRule(RawSearchRule raw, SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {
        return getBuilder(raw).buildSearchRule(raw, typesContainer, transformer, selectorUtils,
            searchRuleExtractor);
    }

    public Map<String, SearchRuleBuilder> getBuilders() {
        return builders;
    }

    public void setBuilders(Map<String, SearchRuleBuilder> builders) {
        this.builders = builders;
    }

    private SearchRuleBuilder getBuilder(RawSearchRule rawSearchRule) {
        return builders.get(rawSearchRule.getGroupName());
    }

}
