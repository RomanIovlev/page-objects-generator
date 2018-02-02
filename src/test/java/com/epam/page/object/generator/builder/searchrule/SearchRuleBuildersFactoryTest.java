package com.epam.page.object.generator.builder.searchrule;

import static org.junit.Assert.*;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.util.PropertyLoader;
import com.epam.page.object.generator.util.RawSearchRuleMapper;
import com.epam.page.object.generator.util.SearchRuleGroups;
import com.epam.page.object.generator.util.SearchRuleGroupsScheme;
import com.epam.page.object.generator.util.TypeTransformer;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import org.junit.Test;

public class SearchRuleBuildersFactoryTest {

    private PropertyLoader propertyLoader = new PropertyLoader("/test-property-file.json");
    private SearchRuleGroupsScheme searchRuleGroupsScheme = propertyLoader.getMapWithScheme();
    private SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();
    private RawSearchRuleMapper rawSearchRuleMapper = new RawSearchRuleMapper(searchRuleGroupsScheme,
        searchRuleGroupList);
    private SearchRuleBuildersFactory searchRuleBuildersFactory = new SearchRuleBuildersFactory(
        rawSearchRuleMapper);
    private SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();
    private XpathToCssTransformer xpathToCssTransformer = new XpathToCssTransformer();
    private TypeTransformer typeTransformer = new TypeTransformer(supportedTypesContainer,
        xpathToCssTransformer, searchRuleBuildersFactory.getMapWithBuilders());

    @Test
    public void build_TypeTransformer_Valid() {

        assertEquals(5, typeTransformer.getBuilders().size());
        assertNotNull(typeTransformer.getBuilders().get("commonSearchRule"));
        assertNotNull(typeTransformer.getBuilders().get("complexSearchRule"));
        assertNotNull(typeTransformer.getBuilders().get("complexInnerSearchRule"));
        assertNotNull(typeTransformer.getBuilders().get("formSearchRule"));
        assertNotNull(typeTransformer.getBuilders().get("formInnerSearchRule"));
    }

}