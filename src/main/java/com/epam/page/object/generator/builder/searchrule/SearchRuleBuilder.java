package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;

public interface SearchRuleBuilder {

    SearchRule buildSearchRule(RawSearchRule rawSearchRule, SupportedTypesContainer typesContainer,
                               XpathToCssTransformer transformer,
                               SelectorUtils selectorUtils,
                               SearchRuleExtractor searchRuleExtractor);
}
