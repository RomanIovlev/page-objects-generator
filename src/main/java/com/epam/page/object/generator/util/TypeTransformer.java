package com.epam.page.object.generator.util;

import com.epam.page.object.generator.builder.searchrule.SearchRuleBuilder;
import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeTransformer {

    private final static Logger logger = LoggerFactory.getLogger(TypeTransformer.class);

    private SupportedTypesContainer typesContainer;
    private XpathToCssTransformer transformer;

    private Map<String, SearchRuleBuilder> builders;

    /**
     * @param typesContainer {@link SupportedTypesContainer}
     * @param transformer    {@link XpathToCssTransformer}
     * @param builders       {@link SearchRuleBuilder}
     */
    public TypeTransformer(SupportedTypesContainer typesContainer,
                           XpathToCssTransformer transformer,
                           Map<String, SearchRuleBuilder> builders) {
        this.typesContainer = typesContainer;
        this.transformer = transformer;
        this.builders = builders;
    }

    /**
     * this method  filters all invalid RawSearchRules and for each valid one builds {@link SearchRule} with appropriate builder.
     *
     * @param rawSearchRuleList   list of {@link RawSearchRule}
     * @param selectorUtils       {@link SelectorUtils}
     * @param searchRuleExtractor {@link SearchRuleExtractor}
     * @return List of ser
     */
    public List<SearchRule> transform(List<RawSearchRule> rawSearchRuleList,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {
        return rawSearchRuleList.stream()
                .filter(RawSearchRule::isValid)
                .map(rawSearchRule -> {
                    SearchRule searchRule = getBuilder(rawSearchRule)
                            .buildSearchRule(rawSearchRule, typesContainer, transformer, selectorUtils,
                                    searchRuleExtractor);
                    logger.debug("Success transformation " + rawSearchRule + "!\n");
                    return searchRule;
                })
                .collect(Collectors.toList());
    }

    /**
     *
     * @return Map of builders.
     */
    public Map<String, SearchRuleBuilder> getBuilders() {
        return builders;
    }

    /**
     *
     * @param rawSearchRule {@link RawSearchRule}
     * @return return {@link SearchRuleBuilder} by rawSearchRule's group name.
     */
    private SearchRuleBuilder getBuilder(RawSearchRule rawSearchRule) {
        return builders.get(rawSearchRule.getGroupName());
    }
}
