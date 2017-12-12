package com.epam.page.object.generator.testDataBuilders.searchRuleDataTestBuilders;

import com.epam.page.object.generator.builders.searchrule.CommonSearchRuleBuilder;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.searchrule.CommonSearchRule;
import com.epam.page.object.generator.models.searchrule.SearchRule;
import com.epam.page.object.generator.testDataBuilders.RawSearchRuleTestDataBuilder;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;

public class CommonSearchRuleTestDataBuilder {

    private static CommonSearchRuleBuilder builder = new CommonSearchRuleBuilder();
    private static SupportedTypesContainer typesContainer = new SupportedTypesContainer();
    private static XpathToCssTransformer transformer = new XpathToCssTransformer();
    private static SelectorUtils selectorUtils = new SelectorUtils();
    private static SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    public static CommonSearchRule getCommonSearchRule_UniquenessValue_SelectorXpath() {
        RawSearchRule rawSearchRule = RawSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessValue_SelectorXpath();

        SearchRule searchRule = builder
            .buildSearchRule(rawSearchRule, typesContainer, transformer, selectorUtils,
                searchRuleExtractor);

        return (CommonSearchRule) searchRule;
    }

    public static CommonSearchRule getCommonSearchRule_UniquenessText_SelectorXpath() {
        RawSearchRule rawSearchRule = RawSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessText_SelectorXpath();

        SearchRule searchRule = builder
            .buildSearchRule(rawSearchRule, typesContainer, transformer, selectorUtils,
                searchRuleExtractor);

        return (CommonSearchRule) searchRule;
    }

    public static CommonSearchRule getCommonSearchRule_UniquenessValue_SelectorCss() {
        RawSearchRule rawSearchRule = RawSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessValue_SelectorCss();

        SearchRule searchRule = builder
            .buildSearchRule(rawSearchRule, typesContainer, transformer, selectorUtils,
                searchRuleExtractor);

        return (CommonSearchRule) searchRule;
    }

    public static CommonSearchRule getCommonSearchRule_UniquenessText_SelectorCss() {
        RawSearchRule rawSearchRule = RawSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessText_SelectorCss();

        SearchRule searchRule = builder
            .buildSearchRule(rawSearchRule, typesContainer, transformer, selectorUtils,
                searchRuleExtractor);

        return (CommonSearchRule) searchRule;
    }
}
