package com.epam.page.object.generator.databuilder.searchrule;

import com.epam.page.object.generator.builder.searchrule.ComplexInnerSearchRuleBuilder;
import com.epam.page.object.generator.builder.searchrule.ComplexSearchRuleBuilder;
import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.databuilder.RawSearchRuleTestDataBuilder;
import com.epam.page.object.generator.util.PropertyLoader;
import com.epam.page.object.generator.util.RawSearchRuleMapper;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SearchRuleGroupsScheme;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import com.epam.page.object.generator.util.SearchRuleGroups;

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
