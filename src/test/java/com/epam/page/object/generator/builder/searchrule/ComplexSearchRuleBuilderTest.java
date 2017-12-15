package com.epam.page.object.generator.builder.searchrule;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.RawSearchRuleMapper;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ComplexSearchRuleBuilderTest {

    @Mock
    private RawSearchRule rawSearchRule;
    @Mock
    private RawSearchRule innerSearchRule1;
    @Mock
    private RawSearchRule innerSearchRule2;
    @Mock
    private RawSearchRuleMapper mapper;
    @Mock
    private ComplexInnerSearchRuleBuilder builder;

    @InjectMocks
    private ComplexSearchRuleBuilder sut;

    private SupportedTypesContainer container = new SupportedTypesContainer();
    private XpathToCssTransformer transformer = new XpathToCssTransformer();
    private SelectorUtils selectorUtils = new SelectorUtils();
    private SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    private List<RawSearchRule> rawSearchRuleList;

    private ComplexInnerSearchRule complexInnerSearchRule1 = new ComplexInnerSearchRule("text",
        "root", new Selector("css", ".myClass"), transformer);
    private ComplexInnerSearchRule complexInnerSearchRule2 = new ComplexInnerSearchRule(null,
        "list", new Selector("xpath", "//div"), transformer);

    private List<ComplexInnerSearchRule> innerSearchRules = Lists
        .newArrayList(complexInnerSearchRule1, complexInnerSearchRule2);

    private ComplexSearchRule expectedSearchRule = new ComplexSearchRule(SearchRuleType.DROPDOWN,
        innerSearchRules, new ClassAndAnnotationPair(Dropdown.class, JDropdown.class),
        selectorUtils);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        rawSearchRuleList = Lists.newArrayList(innerSearchRule1, innerSearchRule2);
    }

    @Test
    public void buildComplexSearchRule_SuccessTest() {

        when(rawSearchRule.getType()).thenReturn(expectedSearchRule.getType());
        when(mapper.getComplexInnerRawSearchRules(any(RawSearchRule.class)))
            .thenReturn(rawSearchRuleList);
        when(builder.buildSearchRule(any(RawSearchRule.class), any(SupportedTypesContainer.class),
            any(XpathToCssTransformer.class), any(SelectorUtils.class), any(SearchRuleExtractor.class)))
            .thenReturn(complexInnerSearchRule1).thenReturn(complexInnerSearchRule2);

        SearchRule searchRule = sut.buildSearchRule(rawSearchRule, container, transformer,
            selectorUtils, searchRuleExtractor);
        ComplexSearchRule actualSearchRule = (ComplexSearchRule) searchRule;

        assertEquals(expectedSearchRule.getType(), actualSearchRule.getType());
        assertEquals(expectedSearchRule.getClassAndAnnotation().getElementClass(),
            actualSearchRule.getClassAndAnnotation().getElementClass());
        assertEquals(expectedSearchRule.getClassAndAnnotation().getElementAnnotation(),
            actualSearchRule.getClassAndAnnotation().getElementAnnotation());
        assertEquals(expectedSearchRule.getInnerSearchRules(),
            actualSearchRule.getInnerSearchRules());
    }
}