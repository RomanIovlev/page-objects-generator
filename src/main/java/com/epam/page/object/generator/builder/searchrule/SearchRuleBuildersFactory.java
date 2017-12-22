package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.util.RawSearchRuleMapper;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchRuleBuildersFactory {

    private final static Logger logger = LoggerFactory.getLogger(SearchRuleBuildersFactory.class);

    private RawSearchRuleMapper rawSearchRuleMapper;

    public SearchRuleBuildersFactory(RawSearchRuleMapper rawSearchRuleMapper) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
    }

    public Map<String, SearchRuleBuilder> getMapWithBuilders() {
        Map<String, SearchRuleBuilder> builderMap = new HashMap<>();

        logger.info("Start creating map with builders...");
        builderMap.put("commonSearchRule", new CommonSearchRuleBuilder());
        logger.debug("Add CommonSearchRuleBuilder for commonSearchRule");
        builderMap.put("complexSearchRule", new ComplexSearchRuleBuilder(rawSearchRuleMapper,
            new ComplexInnerSearchRuleBuilder()));
        logger.debug("Add ComplexSearchRuleBuilder for complexSearchRule");
        builderMap.put("complexInnerSearchRule", new ComplexInnerSearchRuleBuilder());
        logger.debug("Add ComplexInnerSearchRuleBuilder for complexInnerSearchRule");
        builderMap.put("formSearchRule", new FormSearchRuleBuilder(rawSearchRuleMapper));
        logger.debug("Add FormSearchRuleBuilder for formSearchRule");
        builderMap.put("formInnerSearchRule", new FormInnerSearchRuleBuilder());
        logger.debug("Add FormInnerSearchRuleBuilder for formInnerSearchRule");
        logger.info("Finish creating map with builders\n");

        return builderMap;
    }

}
