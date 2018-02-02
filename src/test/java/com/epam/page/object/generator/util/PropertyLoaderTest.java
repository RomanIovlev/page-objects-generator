package com.epam.page.object.generator.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
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
    public void getSearchRuleGroupList_SearchRuleGroups_Valid() {
        SearchRuleGroups searchRuleGroupList = propertyLoader.getSearchRuleGroupList();

        SearchRuleGroup actualCommonSearchRuleGroup = searchRuleGroupList
            .getGroupByType(SearchRuleType.BUTTON);

        assertEquals(expectedCommonSearchRuleGroup.getName(), actualCommonSearchRuleGroup.getName());
        assertEquals(expectedCommonSearchRuleGroup.getSearchRuleTypes(), actualCommonSearchRuleGroup.getSearchRuleTypes());

        SearchRuleGroup actualComplexSearchRuleGroup = searchRuleGroupList.getGroupByType(SearchRuleType.DROPDOWN);

        assertEquals(expectedComplexSearchRuleGroup.getName(), actualComplexSearchRuleGroup.getName());
        assertEquals(expectedComplexSearchRuleGroup.getSearchRuleTypes(), actualComplexSearchRuleGroup.getSearchRuleTypes());

        SearchRuleGroup actualFormSearchRuleGroup = searchRuleGroupList.getGroupByType(SearchRuleType.FORM);

        assertEquals(expectedFormSearchRuleGroup.getName(), actualFormSearchRuleGroup.getName());
        assertEquals(expectedFormSearchRuleGroup.getSearchRuleTypes(), actualFormSearchRuleGroup.getSearchRuleTypes());
    }

    @Test
    public void load_Schema_Valid() {

        String commonSchema = "/schemaTest/commonSearchRule_schema.json";
        Schema commonSearchRuleSchema;
        try (InputStream commonSchemaStream = PropertyLoaderTest.class
            .getResourceAsStream(commonSchema)) {
            JSONObject jsonSchema = new JSONObject(new JSONTokener(commonSchemaStream));
            commonSearchRuleSchema = SchemaLoader.load(jsonSchema);
        } catch (NullPointerException e) {
            throw new NullPointerException("Schema = '" + commonSchema + "' doesn't exist!");
        } catch (IOException e) {
            throw new NullPointerException("Failed reading schema = '" + commonSchema + "'!");
        }

        String complexSchema = "/schemaTest/complexSearchRule_schema.json";
        Schema complexSearchRuleSchema;
        try (InputStream complexSchemaStream = PropertyLoaderTest.class
            .getResourceAsStream(complexSchema)) {
            JSONObject jsonSchema = new JSONObject(new JSONTokener(complexSchemaStream));
            complexSearchRuleSchema = SchemaLoader.load(jsonSchema);
        } catch (NullPointerException e) {
            throw new NullPointerException("Schema = '" + complexSchema + "' doesn't exist!");
        } catch (IOException e) {
            throw new NullPointerException("Failed reading schema = '" + complexSchema + "'!");
        }

        String formSchema = "/schemaTest/formSearchRule_schema.json";
        Schema formSearchRuleSchema;
        try (InputStream formSchemaStream = PropertyLoaderTest.class
            .getResourceAsStream(formSchema)) {
            JSONObject jsonSchema = new JSONObject(new JSONTokener(formSchemaStream));
            formSearchRuleSchema = SchemaLoader.load(jsonSchema);
        } catch (NullPointerException e) {
            throw new NullPointerException("Schema = '" + formSchema + "' doesn't exist!");
        } catch (IOException e) {
            throw new NullPointerException("Failed reading schema = '" + formSchema + "'!");
        }

        SearchRuleGroupsScheme actualSchemaMap = propertyLoader.getMapWithScheme();

        assertEquals(commonSearchRuleSchema, actualSchemaMap.getSchema("commonSearchRule"));
        assertEquals(complexSearchRuleSchema, actualSchemaMap.getSchema("complexSearchRule"));
        assertEquals(formSearchRuleSchema, actualSchemaMap.getSchema("formSearchRule"));
    }
}