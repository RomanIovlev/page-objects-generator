package com.epam.page.object.generator.builder.searchrule;

import com.epam.jdi.uitests.web.selenium.elements.common.Input;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
import com.epam.page.object.generator.util.*;
import org.assertj.core.util.Lists;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class FormSearchRuleBuilderTest {

    private PropertyLoader propertyLoader = new PropertyLoader("/test-property-file.json");
    private SearchRuleGroupsScheme searchRuleGroupsScheme = propertyLoader.getMapWithScheme();
    private SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();
    private RawSearchRuleMapper rawSearchRuleMapper = new RawSearchRuleMapper(
        searchRuleGroupsScheme, searchRuleGroupList);

    @Mock
    private RawSearchRule rawSearchRule;
    private SupportedTypesContainer container = new SupportedTypesContainer();
    private XpathToCssTransformer transformer = new XpathToCssTransformer();
    private SelectorUtils selectorUtils = new SelectorUtils();
    private SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();

    @Mock
    private JSONArray array;
    @Mock
    private JSONObject object;

    private FormSearchRule expectedSearchRule = new FormSearchRule("text", SearchRuleType.FORM,
        new Selector("css", ".myClass"), Lists.newArrayList(
        new FormInnerSearchRule("text", SearchRuleType.INPUT,
            new Selector("css", ".myClass"),
            new ClassAndAnnotationPair(Input.class, FindBy.class),
            transformer, searchRuleExtractor)),
        new ClassAndAnnotationPair(Form.class, FindBy.class));

    @Test
    public void build_FormSearchRule_Valid() {
        FormSearchRuleBuilder builder = new FormSearchRuleBuilder(rawSearchRuleMapper);

        assertNotNull(builder);

        MockitoAnnotations.initMocks(this);
        when(array.length()).thenReturn(1);
        when(array.getJSONObject(anyInt())).thenReturn(object);
        when(object.get("type")).thenReturn("form");
        when(rawSearchRule.getType()).thenReturn(expectedSearchRule.getType());
        when(rawSearchRule.getValue("section")).thenReturn(expectedSearchRule.getSection());
        when(rawSearchRule.getSelector()).thenReturn(expectedSearchRule.getSelector());
        when(rawSearchRule.getElement()).thenReturn(object);
        when(rawSearchRule.getElement().getJSONArray("innerSearchRules")).thenReturn(array);

        FormSearchRule searchRule = (FormSearchRule) builder
            .buildSearchRule(rawSearchRule, container, transformer, selectorUtils,
                searchRuleExtractor);

        assertNotNull(searchRule);
        assertTrue(searchRule.isValid());
        assertFalse(searchRule.isInvalid());

        assertEquals(expectedSearchRule.getType(), searchRule.getType());
        assertEquals(expectedSearchRule.getSelector(), searchRule.getSelector());
        assertEquals(expectedSearchRule.getClassAndAnnotation().getElementAnnotation(),
            searchRule.getClassAndAnnotation().getElementAnnotation());
        assertEquals(expectedSearchRule.getClassAndAnnotation().getElementClass(),
            searchRule.getClassAndAnnotation().getElementClass());
    }
}