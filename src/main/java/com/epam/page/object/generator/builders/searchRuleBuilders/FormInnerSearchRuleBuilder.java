package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.models.ClassAndAnnotationPair;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.models.searchRules.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;

public class FormInnerSearchRuleBuilder implements SearchRuleBuilder {

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {

        SearchRuleType type = rawSearchRule.getType();
        String uniqueness = rawSearchRule.getValue("uniqueness");
        Selector selector = rawSearchRule.getSelector();
        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());

        return new FormInnerSearchRule(uniqueness, type, selector, classAndAnnotation, transformer,
            searchRuleExtractor);
    }
}
