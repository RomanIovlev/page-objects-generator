package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.utils.searchRuleGroups.SearchRuleGroup;
import java.util.ArrayList;
import java.util.List;
import org.everit.json.schema.Schema;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawSearchRuleMapper {

    private final static Logger logger = LoggerFactory.getLogger(RawSearchRuleMapper.class);

    public List<RawSearchRule> getRawSearchRuleList(String json) {
        logger.info("Create list of SearchRules...");
        List<RawSearchRule> rawSearchRuleList = new ArrayList<>();

        JSONObject tree = new JSONObject(
            new JSONTokener(RawSearchRuleMapper.class.getResourceAsStream(json)));
        JSONArray elements = tree.getJSONArray("elements");
        for (int i = 0; i < elements.length(); i++) {
            JSONObject object = elements.getJSONObject(i);
            String searchRuleType = object.get("type").toString();
            SearchRuleType type = SearchRuleType
                .getSearchRuleTypeByString(searchRuleType.toLowerCase());
            SearchRuleGroup group = PropertyLoader.searchRuleGroups.getGroupByType(type);
            Schema schema = PropertyLoader.searchRuleGroupsScheme.getSchema(group.getName());
            RawSearchRule rawSearchRule = new RawSearchRule(object, type, group, schema);
            rawSearchRuleList.add(rawSearchRule);
            logger.info("Add SearchRule ='" + rawSearchRule + "'");
        }
        logger.info("Finish creating list of SearchRules\n");

        return rawSearchRuleList;
    }

    public List<RawSearchRule> getComplexInnerRawSearchRules(RawSearchRule parent) {
        logger.info("Create list of complexInnerSearchRules...");
        List<RawSearchRule> innerRawSearchRules = new ArrayList<>();

        JSONArray innerSearchRules = parent.getElement().getJSONArray("innerSearchRules");
        for (int i = 0; i < innerSearchRules.length(); i++) {
            JSONObject object = innerSearchRules.getJSONObject(i);
            SearchRuleType type = null;
            SearchRuleGroup group = null;
            Schema schema = PropertyLoader.searchRuleGroupsScheme
                .getSchema("complexInnerSearchRule");
            RawSearchRule rawSearchRule = new RawSearchRule(object, type, group, schema);
            innerRawSearchRules.add(rawSearchRule);
            logger.info("Add complexInnerSearchRule ='" + rawSearchRule + "'");
        }
        logger.info("Finish creating list of complexInnerSearchRules\n");

        return innerRawSearchRules;
    }

    public List<RawSearchRule> getFormInnerRawSearchRules(RawSearchRule parent) {
        logger.info("Create list of formInnerSearchRules...");
        List<RawSearchRule> innerRawSearchRules = new ArrayList<>();

        JSONArray innerSearchRules = parent.getElement().getJSONArray("innerSearchRules");
        for (int i = 0; i < innerSearchRules.length(); i++) {
            JSONObject object = innerSearchRules.getJSONObject(i);
            String searchRuleType = object.get("type").toString();
            SearchRuleType type = SearchRuleType
                .getSearchRuleTypeByString(searchRuleType.toLowerCase());
            SearchRuleGroup group = null;
            Schema schema = PropertyLoader.searchRuleGroupsScheme
                .getSchema("formInnerSearchRule");
            RawSearchRule rawSearchRule = new RawSearchRule(object, type, group, schema);
            innerRawSearchRules.add(rawSearchRule);
            logger.info("Add formInnerSearchRule ='" + rawSearchRule + "'");
        }
        logger.info("Finish creating list of formInnerSearchRule\n");

        return innerRawSearchRules;
    }
}
