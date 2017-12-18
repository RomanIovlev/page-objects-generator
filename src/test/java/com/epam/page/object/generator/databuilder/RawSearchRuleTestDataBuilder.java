package com.epam.page.object.generator.databuilder;

import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.util.PropertyLoader;
import com.epam.page.object.generator.util.RawSearchRuleMapper;
import com.epam.page.object.generator.util.SearchRuleGroupsScheme;
import com.epam.page.object.generator.util.SearchRuleGroups;
import java.util.List;

public class RawSearchRuleTestDataBuilder {

    private static final String DEFAULT_JSON_PATH = "/jsonForBuilders";
    private static PropertyLoader propertyLoader = new PropertyLoader("/groups.json");
    private static SearchRuleGroupsScheme searchRuleGroupsScheme = propertyLoader
        .getMapWithScheme();
    private static SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();
    private static RawSearchRuleMapper parser = new RawSearchRuleMapper(searchRuleGroupsScheme,
        searchRuleGroupList);

    public static RawSearchRule getCommonSearchRule_UniquenessValue_SelectorXpath() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH
                + "/commonSearchRule/uniqueness-Value-selector-Xpath.json");

        return rawSearchRuleList.get(0);
    }

    public static RawSearchRule getCommonSearchRule_UniquenessText_SelectorXpath() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH
                + "/commonSearchRule/uniqueness-Text-selector-Xpath.json");

        return rawSearchRuleList.get(0);
    }

    public static RawSearchRule getCommonSearchRule_UniquenessValue_SelectorCss() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH
                + "/commonSearchRule/uniqueness-Value-selector-Css.json");

        return rawSearchRuleList.get(0);
    }

    public static RawSearchRule getCommonSearchRule_UniquenessText_SelectorCss() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH
                + "/commonSearchRule/uniqueness-Text-selector-Css.json");

        return rawSearchRuleList.get(0);
    }

    public static RawSearchRule getCommonSearchRule_DoesNotPassJsonSchemaValidator() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH
                + "/commonSearchRule/invalid-json-schema-validator.json");

        return rawSearchRuleList.get(0);
    }

    public static RawSearchRule getComplexSearchRule_WithRoot() {
        List<RawSearchRule> rawSearchRules = parser.getRawSearchRuleList(
            DEFAULT_JSON_PATH + "/complexSearchRule/only-root-title.json");

        return rawSearchRules.get(0);
    }

    public static RawSearchRule getComplexSearchRule_WithDuplicateTitles() {
        List<RawSearchRule> rawSearchRules = parser.getRawSearchRuleList(
            DEFAULT_JSON_PATH + "/complexSearchRule/duplicate-title.json");

        return rawSearchRules.get(0);
    }

    public static RawSearchRule getComplexSearchRule_WithRootAndList() {
        List<RawSearchRule> rawSearchRules = parser.getRawSearchRuleList(
            DEFAULT_JSON_PATH + "/complexSearchRule/root-and-list.json");

        return rawSearchRules.get(0);
    }
}
