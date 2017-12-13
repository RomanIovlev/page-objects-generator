package com.epam.page.object.generator.builders.searchrule;

import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleGroupsScheme;
import com.epam.page.object.generator.utils.SearchRuleGroups;
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

    public SearchRuleBuilders getMapWithBuilders() {
        Map<String, SearchRuleBuilder> builderMap = new HashMap<>();

        logger.info("Create map with builders");
        builderMap.put("commonSearchRule", new CommonSearchRuleBuilder());
        builderMap.put("complexSearchRule", new ComplexSearchRuleBuilder(rawSearchRuleMapper,
            new ComplexInnerSearchRuleBuilder()));
        builderMap.put("complexInnerSearchRule", new ComplexInnerSearchRuleBuilder());
        builderMap.put("formSearchRule", new FormSearchRuleBuilder(rawSearchRuleMapper));
        builderMap.put("formInnerSearchRule", new FormInnerSearchRuleBuilder());

        return new SearchRuleBuilders(builderMap);
    }

}
