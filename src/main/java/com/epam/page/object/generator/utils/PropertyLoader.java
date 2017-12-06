package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.builders.searchRuleBuilders.CommonSearchRuleBuilder;
import com.epam.page.object.generator.builders.searchRuleBuilders.ComplexInnerSearchRuleBuilder;
import com.epam.page.object.generator.builders.searchRuleBuilders.ComplexSearchRuleBuilder;
import com.epam.page.object.generator.builders.searchRuleBuilders.FormInnerSearchRuleBuilder;
import com.epam.page.object.generator.builders.searchRuleBuilders.FormSearchRuleBuilder;
import com.epam.page.object.generator.builders.RawSearchRuleBuilder;
import com.epam.page.object.generator.builders.RawSearchRuleBuilders;
import com.epam.page.object.generator.utils.searchRuleGroups.SearchRuleGroup;
import com.epam.page.object.generator.utils.searchRuleGroups.SearchRuleGroups;
import com.epam.page.object.generator.utils.searchRuleScheme.SearchRuleGroupsScheme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropertyLoader {

    private final static String PROPERTY_FILE = "/groups.json";

    static SearchRuleGroups searchRuleGroups;
    static SearchRuleGroupsScheme searchRuleGroupsScheme;
    public static RawSearchRuleBuilders rawSearchRuleBuilders;

    private final static Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

    public static void loadProperties() {
        logger.info("Start read property file = '" + PROPERTY_FILE + "'\n");
        searchRuleGroups = new SearchRuleGroups(getSearchRuleGroupList());
        searchRuleGroupsScheme = new SearchRuleGroupsScheme(getMapWithScheme());
        rawSearchRuleBuilders = new RawSearchRuleBuilders(getMapWithBuilders());
    }

    private static Map<String, RawSearchRuleBuilder> getMapWithBuilders() {
        Map<String, RawSearchRuleBuilder> builderMap = new HashMap<>();

        logger.info("Create map with builders");
        builderMap.put("commonSearchRule", new CommonSearchRuleBuilder());
        builderMap
            .put("complexSearchRule", new ComplexSearchRuleBuilder(new RawSearchRuleMapper()));
        builderMap.put("complexInnerSearchRule", new ComplexInnerSearchRuleBuilder());
        builderMap.put("formSearchRule", new FormSearchRuleBuilder(new RawSearchRuleMapper()));
        builderMap.put("formInnerSearchRule", new FormInnerSearchRuleBuilder());

        return builderMap;
    }

    private static Map<String, Schema> getMapWithScheme() {
        Map<String, Schema> schemeMap = new HashMap<>();

        logger.info("Start reading list of schemes...");
        JSONObject jsonObject = new JSONObject(
            new JSONTokener(PropertyLoader.class.getResourceAsStream(PROPERTY_FILE)));

        JSONArray typeGroups = jsonObject.getJSONArray("typeGroups");
        for (int i = 0; i < typeGroups.length(); i++) {

            JSONObject group = typeGroups.getJSONObject(i);
            String groupName = group.getString("name");
            logger.info("Read '" + groupName + "' group...");

            String schemaPath = group.getString("schema");

            JSONObject jsonSchema = new JSONObject(
                new JSONTokener(
                    PropertyLoader.class.getResourceAsStream(schemaPath)));
            Schema schema = SchemaLoader.load(jsonSchema);
            schemeMap.put(groupName, schema);
            logger.info("Add scheme = '" + schemaPath + "' for '" + groupName + "' group");
        }
        logger.info("Finish reading list of schemes\n");

        return schemeMap;
    }

    private static List<SearchRuleGroup> getSearchRuleGroupList() {
        List<SearchRuleGroup> searchRuleGroups = new ArrayList<>();

        logger.info("Start reading list of group types...");
        JSONObject jsonObject = new JSONObject(
            new JSONTokener(PropertyLoader.class.getResourceAsStream(PROPERTY_FILE)));

        JSONArray typeGroups = jsonObject.getJSONArray("typeGroups");
        for (int i = 0; i < typeGroups.length(); i++) {
            JSONObject group = typeGroups.getJSONObject(i);

            String groupName = group.getString("name");
            logger.info("Read '" + groupName + "' group...");
            List<SearchRuleType> searchRuleTypes = new ArrayList<>();
            JSONArray types = group.getJSONArray("searchRuleTypes");
            for (int j = 0; j < types.length(); j++) {
                searchRuleTypes.add(SearchRuleType.getSearchRuleTypeByString(types.getString(j)));
                logger.info("Add type = '" + types.getString(j) + "' in '" + groupName + "' group");
            }
            searchRuleGroups.add(new SearchRuleGroup(groupName, searchRuleTypes));
            logger.info("Finish read group types - '" + groupName + "'\n");
        }
        logger.info("Finish reading list of group types\n");

        return searchRuleGroups;
    }


}
