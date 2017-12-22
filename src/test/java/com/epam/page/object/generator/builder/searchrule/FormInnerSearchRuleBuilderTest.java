package com.epam.page.object.generator.builder.searchrule;

import com.epam.jdi.uitests.web.selenium.elements.common.Input;
import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class FormInnerSearchRuleBuilderTest {

    @Mock
    private RawSearchRule rawSearchRule;
    private SupportedTypesContainer container = new SupportedTypesContainer();
    private XpathToCssTransformer transformer = new XpathToCssTransformer();
    private SelectorUtils selectorUtils = new SelectorUtils();
    private SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    private FormInnerSearchRule expectedSearchRule  = new FormInnerSearchRule("text", SearchRuleType.INPUT,
    new Selector("css", ".myClass"), new ClassAndAnnotationPair(Input.class, FindBy.class),transformer,searchRuleExtractor);

    @Test
    public void buildSearchRule() {
        MockitoAnnotations.initMocks(this);
        FormInnerSearchRuleBuilder builder = new FormInnerSearchRuleBuilder();

        when(rawSearchRule.getType()).thenReturn(expectedSearchRule.getType());
        when(rawSearchRule.getValue("uniqueness")).thenReturn(expectedSearchRule.getUniqueness());
        when(rawSearchRule.getSelector()).thenReturn(expectedSearchRule.getSelector());

        FormInnerSearchRule searchRule = (FormInnerSearchRule)
                builder.buildSearchRule(rawSearchRule, container,transformer, selectorUtils,searchRuleExtractor);

        assertNotNull(searchRule);
        assertEquals(expectedSearchRule.getUniqueness(), searchRule.getUniqueness());
        assertEquals(expectedSearchRule.getType(), searchRule.getType());
        assertEquals(expectedSearchRule.getSelector(), searchRule.getSelector());
        assertEquals(expectedSearchRule.getClassAndAnnotation().getElementAnnotation(), searchRule.getClassAndAnnotation().getElementAnnotation());
        assertEquals(expectedSearchRule.getClassAndAnnotation().getElementClass(), searchRule.getClassAndAnnotation().getElementClass());
        assertEquals(expectedSearchRule.getTransformedSelector(), searchRule.getTransformedSelector());
    }
}