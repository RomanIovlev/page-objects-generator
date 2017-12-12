package com.epam.page.object.generator.utils;

import static org.junit.Assert.*;

import org.assertj.core.util.Lists;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

public class PropertyLoaderTest {

    private PropertyLoader propertyLoader = new PropertyLoader("/test-property-file.json");
    private SearchRuleGroup expectedCommonSearchRuleGroup = new SearchRuleGroup("commonSearchRule",
        Lists.newArrayList(SearchRuleType.BUTTON, SearchRuleType.TEXT));
    private SearchRuleGroup expectedComplexSearchRuleGroup = new SearchRuleGroup(
        "complexSearchRule",
        Lists.newArrayList(SearchRuleType.DROPDOWN, SearchRuleType.MENU));
    private SearchRuleGroup expectedFormSearchRuleGroup = new SearchRuleGroup("formSearchRule",
        Lists.newArrayList(SearchRuleType.FORM));


    @Test
    public void getSearchRuleGroupList() {
        SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();

        SearchRuleGroup actualCommonSearchRuleGroup = searchRuleGroupList
            .getGroupByType(SearchRuleType.BUTTON);

        assertEquals(expectedCommonSearchRuleGroup.getName(),
            actualCommonSearchRuleGroup.getName());
        assertEquals(expectedCommonSearchRuleGroup.getSearchRuleTypes(),
            actualCommonSearchRuleGroup.getSearchRuleTypes());

        SearchRuleGroup actualComplexSearchRuleGroup = searchRuleGroupList
            .getGroupByType(SearchRuleType.DROPDOWN);

        assertEquals(expectedComplexSearchRuleGroup.getName(),
            actualComplexSearchRuleGroup.getName());
        assertEquals(expectedComplexSearchRuleGroup.getSearchRuleTypes(),
            actualComplexSearchRuleGroup.getSearchRuleTypes());

        SearchRuleGroup actualFormSearchRuleGroup = searchRuleGroupList
            .getGroupByType(SearchRuleType.FORM);

        assertEquals(expectedFormSearchRuleGroup.getName(), actualFormSearchRuleGroup.getName());
        assertEquals(expectedFormSearchRuleGroup.getSearchRuleTypes(),
            actualFormSearchRuleGroup.getSearchRuleTypes());
    }

    @Test
    public void getMapWithScheme() {

        JSONObject jsonSchema = new JSONObject(
            new JSONTokener(
                PropertyLoaderTest.class
                    .getResourceAsStream("/schemaTest/commonSearchRule_schema.json")));
        Schema commonSearchRuleSchema = SchemaLoader.load(jsonSchema);

        jsonSchema = new JSONObject(
            new JSONTokener(
                PropertyLoaderTest.class
                    .getResourceAsStream("/schemaTest/complexSearchRule_schema.json")));
        Schema complexSearchRuleSchema = SchemaLoader.load(jsonSchema);

        jsonSchema = new JSONObject(
            new JSONTokener(
                PropertyLoaderTest.class
                    .getResourceAsStream("/schemaTest/formSearchRule_schema.json")));
        Schema formSearchRuleSchema = SchemaLoader.load(jsonSchema);

        SearchRuleGroupsScheme actualSchemaMap = propertyLoader.getMapWithScheme();

        assertEquals(commonSearchRuleSchema, actualSchemaMap.getSchema("commonSearchRule"));
        assertEquals(complexSearchRuleSchema, actualSchemaMap.getSchema("complexSearchRule"));
        assertEquals(formSearchRuleSchema, actualSchemaMap.getSchema("formSearchRule"));
    }
}