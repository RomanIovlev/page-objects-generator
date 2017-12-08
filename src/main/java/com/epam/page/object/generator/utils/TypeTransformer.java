package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.builders.searchRuleBuilders.SearchRuleBuilders;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeTransformer {

    private final static Logger logger = LoggerFactory.getLogger(TypeTransformer.class);

    private SupportedTypesContainer typesContainer;
    private SearchRuleBuilders searchRuleBuilders;
    private XpathToCssTransformer transformer;

    public TypeTransformer(SupportedTypesContainer typesContainer,
                           SearchRuleBuilders searchRuleBuilders,
                           XpathToCssTransformer transformer) {
        this.typesContainer = typesContainer;
        this.searchRuleBuilders = searchRuleBuilders;
        this.transformer = transformer;
    }

    public List<SearchRule> transform(List<RawSearchRule> rawSearchRuleList) {
        return rawSearchRuleList.stream()
            .filter(RawSearchRule::isValid)
            .map(rawSearchRule -> {
                SearchRule searchRule = searchRuleBuilders
                    .buildSearchRule(rawSearchRule, typesContainer, transformer);
                logger.info("Success transformation " + rawSearchRule + "!");
                return searchRule;
            })
            .collect(Collectors.toList());
    }
}
