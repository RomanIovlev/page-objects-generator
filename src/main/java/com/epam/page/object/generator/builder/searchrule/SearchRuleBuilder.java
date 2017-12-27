package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;

/**
 * This {@link SearchRuleBuilder} interface describes how to create a {@link SearchRule} from an
 * existing {@link RawSearchRule} using supported types of elements.
 *
 * @see CommonSearchRuleBuilder
 * @see CommonSearchRuleBuilder
 * @see ComplexInnerSearchRuleBuilder
 * @see FormSearchRuleBuilder
 * @see FormInnerSearchRuleBuilder
 */
public interface SearchRuleBuilder {

    /**
     * Method creates a {@link SearchRule} from an existing {@link RawSearchRule}
     *
     * @param rawSearchRule unprocessed(raw) search rule for transformation to {@link SearchRule}
     * @param typesContainer current supported types of elements
     * @param transformer transforms xpath {@link Selector} to css
     * @param selectorUtils generator string contains css or xpath selector for SearchRule.
     * @param searchRuleExtractor {@link SearchRuleExtractor} by default can be null
     * @return transformed {@link SearchRule}
     */
    SearchRule buildSearchRule(RawSearchRule rawSearchRule, SupportedTypesContainer typesContainer,
                               XpathToCssTransformer transformer,
                               SelectorUtils selectorUtils,
                               SearchRuleExtractor searchRuleExtractor);
}
