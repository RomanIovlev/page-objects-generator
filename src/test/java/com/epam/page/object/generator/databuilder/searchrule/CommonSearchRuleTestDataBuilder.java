package com.epam.page.object.generator.databuilder.searchrule;

import com.epam.page.object.generator.builder.searchrule.CommonSearchRuleBuilder;
import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.databuilder.RawSearchRuleTestDataBuilder;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;

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
