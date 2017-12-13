package com.epam.page.object.generator.testDataBuilders.searchRuleTestDataBuilders;

import com.epam.page.object.generator.builders.searchrule.ComplexInnerSearchRuleBuilder;
import com.epam.page.object.generator.builders.searchrule.ComplexSearchRuleBuilder;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.models.searchrule.SearchRule;
import com.epam.page.object.generator.testDataBuilders.RawSearchRuleTestDataBuilder;
import com.epam.page.object.generator.utils.PropertyLoader;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleGroupsScheme;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.epam.page.object.generator.utils.SearchRuleGroups;

public class ComplexSearchRuleTestDataBuilder {

    private static PropertyLoader propertyLoader = new PropertyLoader("/groups.json");
    private static SearchRuleGroupsScheme searchRuleGroupsScheme = propertyLoader
        .getMapWithScheme();
    private static SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();
    private static RawSearchRuleMapper rawSearchRuleMapper = new RawSearchRuleMapper(
        searchRuleGroupsScheme,
        searchRuleGroupList);
    private static ComplexInnerSearchRuleBuilder complexInnerSearchRuleBuilder = new ComplexInnerSearchRuleBuilder();

    private static ComplexSearchRuleBuilder builder = new ComplexSearchRuleBuilder(
        rawSearchRuleMapper, complexInnerSearchRuleBuilder);
    private static SupportedTypesContainer typesContainer = new SupportedTypesContainer();
    private static XpathToCssTransformer transformer = new XpathToCssTransformer();
    private static SelectorUtils selectorUtils = new SelectorUtils();
    private static SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    public static ComplexSearchRule getComplexSearchRule_WithRoot() {
        RawSearchRule rawSearchRule = RawSearchRuleTestDataBuilder
            .getComplexSearchRule_WithRoot();

        SearchRule searchRule = builder
            .buildSearchRule(rawSearchRule, typesContainer, transformer, selectorUtils,
                searchRuleExtractor);

        return (ComplexSearchRule) searchRule;
    }

    public static ComplexSearchRule getComplexSearchRule_WithDuplicateTitle() {
        RawSearchRule rawSearchRule = RawSearchRuleTestDataBuilder
            .getComplexSearchRule_WithDuplicateTitles();

        SearchRule searchRule = builder
            .buildSearchRule(rawSearchRule, typesContainer, transformer, selectorUtils,
                searchRuleExtractor);

        return (ComplexSearchRule) searchRule;
    }

    public static ComplexSearchRule getComplexSearchRule_WithRootAndList() {
        RawSearchRule rawSearchRule = RawSearchRuleTestDataBuilder
            .getComplexSearchRule_WithRootAndList();

        SearchRule searchRule = builder
            .buildSearchRule(rawSearchRule, typesContainer, transformer, selectorUtils,
                searchRuleExtractor);

        return (ComplexSearchRule) searchRule;
    }

}
