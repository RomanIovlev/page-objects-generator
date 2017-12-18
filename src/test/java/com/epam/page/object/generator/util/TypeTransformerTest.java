package com.epam.page.object.generator.util;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.page.object.generator.builder.searchrule.CommonSearchRuleBuilder;
import com.epam.page.object.generator.builder.searchrule.SearchRuleBuilder;
import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.databuilder.RawSearchRuleTestDataBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.support.FindBy;

public class TypeTransformerTest {

    @Mock
    private CommonSearchRuleBuilder commonSearchRuleBuilder;

    private SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();
    private XpathToCssTransformer xpathToCssTransformer = new XpathToCssTransformer();
    private TypeTransformer typeTransformer;


    private SelectorUtils selectorUtils = new SelectorUtils();
    private SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    private SearchRule expectedSearchRule = new CommonSearchRule("text",
        SearchRuleType.BUTTON,
        new Selector("css", "input[type=submit]"), new ClassAndAnnotationPair(
        Button.class, FindBy.class), xpathToCssTransformer, selectorUtils);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        typeTransformer = new TypeTransformer(supportedTypesContainer, xpathToCssTransformer,
            getSearchRuleBuilders());
    }

    @Test
    public void transform() {
        when(commonSearchRuleBuilder
            .buildSearchRule(any(RawSearchRule.class), any(SupportedTypesContainer.class),
                any(XpathToCssTransformer.class), any(SelectorUtils.class),
                any(SearchRuleExtractor.class)))
            .thenReturn(expectedSearchRule);

        List<RawSearchRule> rawSearchRules = Lists.newArrayList(RawSearchRuleTestDataBuilder
            .getCommonSearchRule_UniquenessText_SelectorCss());

        List<SearchRule> actualSearchRule = typeTransformer
            .transform(rawSearchRules, selectorUtils, searchRuleExtractor);

        assertEquals(expectedSearchRule, actualSearchRule.get(0));
    }

    @Test
    public void getBuilders() {
        assertEquals(1, typeTransformer.getBuilders().size());
        assertEquals(commonSearchRuleBuilder,
            typeTransformer.getBuilders().get("commonSearchRule"));
    }

    private Map<String, SearchRuleBuilder> getSearchRuleBuilders() {
        Map<String, SearchRuleBuilder> builderMap = new HashMap<>();
        builderMap.put("commonSearchRule", commonSearchRuleBuilder);
        return builderMap;
    }
}