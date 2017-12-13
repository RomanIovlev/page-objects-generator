package com.epam.page.object.generator.builder.searchrule;

import static org.junit.Assert.*;

import com.epam.page.object.generator.util.PropertyLoader;
import com.epam.page.object.generator.util.RawSearchRuleMapper;
import com.epam.page.object.generator.util.SearchRuleGroups;
import com.epam.page.object.generator.util.SearchRuleGroupsScheme;
import org.junit.Test;

public class SearchRuleBuildersFactoryTest {

    private PropertyLoader propertyLoader = new PropertyLoader("/test-property-file.json");
    private SearchRuleGroupsScheme searchRuleGroupsScheme = propertyLoader.getMapWithScheme();
    private SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();
    RawSearchRuleMapper rawSearchRuleMapper = new RawSearchRuleMapper(searchRuleGroupsScheme,
        searchRuleGroupList);
    private SearchRuleBuildersFactory searchRuleBuildersFactory = new SearchRuleBuildersFactory(
        rawSearchRuleMapper);

    @Test
    public void getMapWithBuilders(){
        SearchRuleBuilders searchRuleBuilders = searchRuleBuildersFactory.getMapWithBuilders();

        assertEquals(5, searchRuleBuilders.getBuilders().size());
        assertNotNull(searchRuleBuilders.getBuilders().get("commonSearchRule"));
        assertNotNull(searchRuleBuilders.getBuilders().get("complexSearchRule"));
        assertNotNull(searchRuleBuilders.getBuilders().get("complexInnerSearchRule"));
        assertNotNull(searchRuleBuilders.getBuilders().get("formSearchRule"));
        assertNotNull(searchRuleBuilders.getBuilders().get("formInnerSearchRule"));
    }

}