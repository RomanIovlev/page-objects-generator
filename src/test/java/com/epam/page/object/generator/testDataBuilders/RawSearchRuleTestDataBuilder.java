package com.epam.page.object.generator.testDataBuilders;

import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.utils.PropertyLoader;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleGroupsScheme;
import com.epam.page.object.generator.utils.searchRuleGroups.SearchRuleGroups;
import java.util.List;

public class RawSearchRuleTestDataBuilder {

    private static final String DEFAULT_JSON_PATH = "/jsonForBuilders";
    private static PropertyLoader propertyLoader = new PropertyLoader("/groups.json");
    private static SearchRuleGroupsScheme searchRuleGroupsScheme = propertyLoader.getMapWithScheme();
    private static SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();
    private static RawSearchRuleMapper parser = new RawSearchRuleMapper(searchRuleGroupsScheme,
        searchRuleGroupList);

    public static RawSearchRule getCommonSearchRule_UniquenessValue_SelectorXpath() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH + "/commonSearchRule/commonSearchRule_UniquenessValue_SelectorXpath.json");

        return rawSearchRuleList.get(0);
    }

    public static RawSearchRule getCommonSearchRule_UniquenessText_SelectorXpath() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH + "/commonSearchRule/commonSearchRule_UniquenessText_SelectorXpath.json");

        return rawSearchRuleList.get(0);
    }

    public static RawSearchRule getCommonSearchRule_UniquenessValue_SelectorCss() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH + "/commonSearchRule/commonSearchRule_UniquenessValue_SelectorCss.json");

        return rawSearchRuleList.get(0);
    }

    public static RawSearchRule getCommonSearchRule_UniquenessText_SelectorCss() {
        List<RawSearchRule> rawSearchRuleList = parser
            .getRawSearchRuleList(DEFAULT_JSON_PATH + "/commonSearchRule/commonSearchRule_UniquenessText_SelectorCss.json");

        return rawSearchRuleList.get(0);
    }
}
