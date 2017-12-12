package com.epam.page.object.generator.builders.searchrule;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.searchrule.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;

public interface SearchRuleBuilder {

    SearchRule buildSearchRule(RawSearchRule rawSearchRule, SupportedTypesContainer typesContainer,
                               XpathToCssTransformer transformer,
                               SelectorUtils selectorUtils,
                               SearchRuleExtractor searchRuleExtractor);
}
