package com.epam.page.object.generator.utils;

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

    public TypeTransformer(SupportedTypesContainer typesContainer) {
        this.typesContainer = typesContainer;
    }

    public List<SearchRule> transform(List<RawSearchRule> rawSearchRuleList) {
        if (rawSearchRuleList.stream().anyMatch(RawSearchRule::isInvalid)) {
            printAllInvalidRawSearchRules(rawSearchRuleList);
        }

        return rawSearchRuleList.stream()
            .filter(RawSearchRule::isValid)
            .map(rawSearchRule -> {
                SearchRule searchRule = PropertyLoader.searchRuleBuilders
                    .getBuilder(rawSearchRule).buildSearchRule(rawSearchRule, typesContainer);
                logger.info("Success transformation " + rawSearchRule + "!");
                return searchRule;
            })
            .collect(Collectors.toList());

    }

    private void printAllInvalidRawSearchRules(List<RawSearchRule> rawSearchRuleList) {
        StringBuilder stringBuilder = new StringBuilder("Json file has invalid search rules:\n");
        rawSearchRuleList.stream()
            .filter(RawSearchRule::isInvalid)
            .forEach(
                rawSearchRule -> stringBuilder.append("\n").append(rawSearchRule)
                    .append(" is invalid:\n").append(rawSearchRule.getExceptionMessage()));
        logger.error(stringBuilder.toString());
    }


}
