package com.epam.page.object.generator.builders.searchRuleBuilders;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.List;
import org.assertj.core.util.Lists;
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

    private List<RawSearchRule> rawSearchRuleList = Lists
        .newArrayList(innerSearchRule1, innerSearchRule2);

    private ComplexInnerSearchRule complexInnerSearchRule1 = new ComplexInnerSearchRule("text",
        "root", new Selector("css", ".myClass"));
    private ComplexInnerSearchRule complexInnerSearchRule2 = new ComplexInnerSearchRule(null,
        "list", new Selector("xpath", "//div"));

    private List<ComplexInnerSearchRule> innerSearchRules = Lists
        .newArrayList(complexInnerSearchRule1, complexInnerSearchRule2);

    private ComplexSearchRule expectedSearchRule = new ComplexSearchRule(SearchRuleType.DROPDOWN,
        innerSearchRules, new ClassAndAnnotationPair(Dropdown.class, JDropdown.class));

    @Test
    public void buildComplexSearchRule_SuccessTest() {
        MockitoAnnotations.initMocks(this);

        when(rawSearchRule.getType()).thenReturn(expectedSearchRule.getType());
        when(mapper.getComplexInnerRawSearchRules(any(RawSearchRule.class)))
            .thenReturn(rawSearchRuleList);
        when(builder.buildSearchRule(any(RawSearchRule.class), any(SupportedTypesContainer.class)))
            .thenReturn(complexInnerSearchRule1).thenReturn(complexInnerSearchRule2);

        SearchRule searchRule = sut.buildSearchRule(rawSearchRule, container);
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