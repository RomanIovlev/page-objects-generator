package com.epam.page.object.generator.builder.searchrule;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.testDataBuilders.RawSearchRuleTestDataBuilder;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.support.FindBy;

public class SearchRuleBuildersTest {

    @Mock
    private CommonSearchRuleBuilder commonSearchRuleBuilder;

    private SearchRuleBuilders searchRuleBuilders;
    private SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();
    private XpathToCssTransformer xpathToCssTransformer = new XpathToCssTransformer();
    private SelectorUtils selectorUtils = new SelectorUtils();
    private SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    private SearchRule expectedSearchRule = new CommonSearchRule("text",
        SearchRuleType.BUTTON,
        new Selector("css", "input[type=submit]"), new ClassAndAnnotationPair(
        Button.class, FindBy.class), xpathToCssTransformer, selectorUtils);

    private SearchRuleBuilders getSearchRuleBuilders() {
        Map<String, SearchRuleBuilder> builderMap = new HashMap<>();
        builderMap.put("commonSearchRule", commonSearchRuleBuilder);
        return new SearchRuleBuilders(builderMap);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        searchRuleBuilders = getSearchRuleBuilders();
    }

    @Test
    public void buildSearchRule() {
        when(commonSearchRuleBuilder
            .buildSearchRule(any(RawSearchRule.class), any(SupportedTypesContainer.class),
                any(XpathToCssTransformer.class), any(SelectorUtils.class),
                any(SearchRuleExtractor.class)))
            .thenReturn(expectedSearchRule);

        RawSearchRule rawSearchRule = RawSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessText_SelectorCss();

        SearchRule actualSearchRule = searchRuleBuilders
            .buildSearchRule(rawSearchRule, supportedTypesContainer, xpathToCssTransformer,
                selectorUtils, searchRuleExtractor);

        assertEquals(expectedSearchRule, actualSearchRule);
    }

    @Test
    public void getBuilders(){
        assertEquals(1, searchRuleBuilders.getBuilders().size());
        assertEquals(commonSearchRuleBuilder, searchRuleBuilders.getBuilders().get("commonSearchRule"));
    }
}