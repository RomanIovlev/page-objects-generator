package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ComplexInnerSearchRuleBuilderTest {

    @Mock
    private RawSearchRule rawSearchRule;

    private XpathToCssTransformer transformer = new XpathToCssTransformer();
    private ComplexInnerSearchRuleBuilder builder = new ComplexInnerSearchRuleBuilder();
    private ComplexInnerSearchRule expectedComplexInnerSearchRule = new ComplexInnerSearchRule(
        "text",
        "root", new Selector("css", ".myClass"), transformer);


    @Test
    public void build_ComplexInnerSearchRule_Valid() {
        MockitoAnnotations.initMocks(this);

        SupportedTypesContainer supportedTypesContainer = new SupportedTypesContainer();
        SelectorUtils selectorUtils = new SelectorUtils();
        SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

        when(rawSearchRule.getValue("title")).thenReturn(expectedComplexInnerSearchRule.getTitle());
        when(rawSearchRule.getValue("uniqueness"))
            .thenReturn(expectedComplexInnerSearchRule.getUniqueness());
        when(rawSearchRule.getSelector()).thenReturn(expectedComplexInnerSearchRule.getSelector());

        ComplexInnerSearchRule searchRule = (ComplexInnerSearchRule) builder
            .buildSearchRule(rawSearchRule, supportedTypesContainer, transformer, selectorUtils,
                searchRuleExtractor);
        assertEquals(expectedComplexInnerSearchRule.getTitle(), searchRule.getTitle());
        assertEquals(expectedComplexInnerSearchRule.getUniqueness(), searchRule.getUniqueness());
        assertEquals(expectedComplexInnerSearchRule.getSelector(), searchRule.getSelector());
    }
}