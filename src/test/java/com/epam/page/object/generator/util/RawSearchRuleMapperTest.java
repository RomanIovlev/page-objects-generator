package com.epam.page.object.generator.util;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.model.RawSearchRule;
import java.util.List;
import org.assertj.core.util.Lists;
import org.everit.json.schema.Schema;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RawSearchRuleMapperTest {

    @Mock
    private JSONObject object;
    @Mock
    private JSONArray array;

    @Mock
    private RawSearchRule parent;

    private PropertyLoader propertyLoader = new PropertyLoader("/groups.json");
    private SearchRuleGroupsScheme searchRuleGroupsScheme = propertyLoader.getMapWithScheme();
    private SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();
    private RawSearchRuleMapper mapper = new RawSearchRuleMapper(searchRuleGroupsScheme,
        searchRuleGroupList);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRawSearchRuleList_OneValidRawSearchRule() {
        SearchRuleType type = SearchRuleType.BUTTON;
        RawSearchRule expectedRawSearchRule = new RawSearchRule(object, type,
            searchRuleGroupList.getGroupByType(type),
            searchRuleGroupsScheme.getSchema("commonSearchRule"));
        List<RawSearchRule> expectedList = Lists.newArrayList(expectedRawSearchRule);

        List<RawSearchRule> actualList = mapper.getRawSearchRuleList("/mapper/button.json");

        assertEquals(expectedList.size(), actualList.size());

        RawSearchRule actualRawSearchRule = actualList.get(0);
        assertEquals(expectedRawSearchRule.getType(), actualRawSearchRule.getType());
        assertEquals(expectedRawSearchRule.getSchema(), actualRawSearchRule.getSchema());
        assertEquals(expectedRawSearchRule.getGroupName(), actualRawSearchRule.getGroupName());

        assertTrue(actualRawSearchRule.isValid());
    }

    @Test
    public void getRawSearchRuleList_ListOfValidRawSearchRules() {
        SearchRuleType buttonType = SearchRuleType.BUTTON;
        SearchRuleType dropdownType = SearchRuleType.DROPDOWN;
        SearchRuleType textType = SearchRuleType.TEXT;
        SearchRuleType formType = SearchRuleType.FORM;

        Schema commonSearchRule = searchRuleGroupsScheme.getSchema("commonSearchRule");
        Schema complexSearchRule = searchRuleGroupsScheme.getSchema("complexSearchRule");
        Schema formSearchRule = searchRuleGroupsScheme.getSchema("formSearchRule");

        RawSearchRule expectedButton = new RawSearchRule(object, buttonType,
            searchRuleGroupList.getGroupByType(buttonType),
            commonSearchRule);

        RawSearchRule expectedDropdown = new RawSearchRule(object,
            dropdownType, searchRuleGroupList.getGroupByType(dropdownType),
            complexSearchRule);

        RawSearchRule expectedText = new RawSearchRule(object, textType,
            searchRuleGroupList.getGroupByType(textType), commonSearchRule);

        RawSearchRule expectedForm = new RawSearchRule(object, formType,
            searchRuleGroupList.getGroupByType(formType), formSearchRule);

        List<RawSearchRule> expectedList = Lists
            .newArrayList(expectedButton, expectedDropdown, expectedText, expectedForm);

        List<RawSearchRule> actualList = mapper.getRawSearchRuleList("/mapper/searchRules.json");

        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < actualList.size(); i++) {
            RawSearchRule actualRawSearchRule = actualList.get(i);
            RawSearchRule expectedRawSearchRule = expectedList.get(i);

            assertEquals(expectedRawSearchRule.getType(), actualRawSearchRule.getType());
            assertEquals(expectedRawSearchRule.getSchema(), actualRawSearchRule.getSchema());
            assertEquals(expectedRawSearchRule.getGroupName(), actualRawSearchRule.getGroupName());
        }
    }

    @Test
    public void getRawSearchRuleList_OneInvalidRawSearchRule() {
        RawSearchRule expectedRawSearchRule = new RawSearchRule(object, null, null, null);
        List<RawSearchRule> expectedList = Lists.newArrayList(expectedRawSearchRule);

        List<RawSearchRule> actualList = mapper.getRawSearchRuleList(
            "/mapper/button-wrong-type.json");

        assertEquals(expectedList.size(), actualList.size());

        RawSearchRule actualRawSearchRule = actualList.get(0);
        assertEquals(expectedRawSearchRule.getType(), actualRawSearchRule.getType());
        assertEquals(expectedRawSearchRule.getSchema(), actualRawSearchRule.getSchema());
        assertEquals(null, actualRawSearchRule.getGroupName());

        assertFalse(actualRawSearchRule.isValid());
    }

    @Test
    public void getRawSearchRuleList_ListOfRawSearchRulesWithOneInvalidRule() {
        SearchRuleType buttonType = SearchRuleType.BUTTON;
        SearchRuleType dropdownType = SearchRuleType.DROPDOWN;
        SearchRuleType textType = SearchRuleType.TEXT;

        Schema commonSearchRule = searchRuleGroupsScheme.getSchema("commonSearchRule");
        Schema complexSearchRule = searchRuleGroupsScheme.getSchema("complexSearchRule");

        RawSearchRule expectedButton = new RawSearchRule(object, buttonType,
            searchRuleGroupList.getGroupByType(buttonType),
            commonSearchRule);

        RawSearchRule expectedDropdown = new RawSearchRule(object,
            dropdownType, searchRuleGroupList.getGroupByType(dropdownType),
            complexSearchRule);

        RawSearchRule expectedText = new RawSearchRule(object, textType,
            searchRuleGroupList.getGroupByType(textType), commonSearchRule);

        RawSearchRule expectedForm = new RawSearchRule(object, null, null, null);

        List<RawSearchRule> expectedList = Lists
            .newArrayList(expectedButton, expectedDropdown, expectedText, expectedForm);

        List<RawSearchRule> actualList = mapper
            .getRawSearchRuleList("/mapper/searchRules-wrong-type.json");

        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < actualList.size(); i++) {
            RawSearchRule actualRawSearchRule = actualList.get(i);
            RawSearchRule expectedRawSearchRule = expectedList.get(i);

            assertEquals(expectedRawSearchRule.getType(), actualRawSearchRule.getType());
            assertEquals(expectedRawSearchRule.getSchema(), actualRawSearchRule.getSchema());
            if (actualRawSearchRule.isValid()) {
                assertEquals(expectedRawSearchRule.getGroupName(),
                    actualRawSearchRule.getGroupName());
            } else {
                assertEquals(null, actualRawSearchRule.getGroupName());
            }
        }
    }

    @Test
    public void getComplexInnerRawSearchRules() {
        when(parent.getElement()).thenReturn(object);
        when(object.getJSONArray("innerSearchRules")).thenReturn(array);
        when(array.length()).thenReturn(1);
        when(array.getJSONObject(anyInt())).thenReturn(object);

        RawSearchRule expectedInnerRawSearchRule = new RawSearchRule(object, null, null,
            searchRuleGroupsScheme.getSchema("complexInnerSearchRule"));

        List<RawSearchRule> expectedList = Lists.newArrayList(expectedInnerRawSearchRule);
        List<RawSearchRule> actualList = mapper.getComplexInnerRawSearchRules(parent);

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedInnerRawSearchRule.getSchema(), actualList.get(0).getSchema());
    }

    @Test
    public void getFormInnerRawSearchRules(){
        when(parent.getElement()).thenReturn(object);
        when(object.getJSONArray("innerSearchRules")).thenReturn(array);
        when(array.length()).thenReturn(1);
        when(array.getJSONObject(anyInt())).thenReturn(object);
        when(object.get("type")).thenReturn("button");

        RawSearchRule expectedInnerRawSearchRule = new RawSearchRule(object, SearchRuleType.BUTTON, null,
            searchRuleGroupsScheme.getSchema("formInnerSearchRule"));

        List<RawSearchRule> expectedList = Lists.newArrayList(expectedInnerRawSearchRule);
        List<RawSearchRule> actualList = mapper.getFormInnerRawSearchRules(parent);

        RawSearchRule actualInnerRawSearchRule = actualList.get(0);

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedInnerRawSearchRule.getType(), actualInnerRawSearchRule.getType());
        assertEquals(expectedInnerRawSearchRule.getSchema(), actualInnerRawSearchRule.getSchema());
    }
}