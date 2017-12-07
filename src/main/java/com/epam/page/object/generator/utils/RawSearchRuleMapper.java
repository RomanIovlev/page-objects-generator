package com.epam.page.object.generator.utils;

import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.utils.searchRuleGroups.SearchRuleGroup;
import com.epam.page.object.generator.validators.ValidationResult;
import java.util.ArrayList;
import java.util.Collections;
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
        List<RawSearchRule> rawSearchRuleList = new ArrayList<>();

        JSONObject tree = new JSONObject(
            new JSONTokener(RawSearchRuleMapper.class.getResourceAsStream(json)));
        JSONArray elements = tree.getJSONArray("elements");
        for (int i = 0; i < elements.length(); i++) {
            JSONObject object = elements.getJSONObject(i);
            String searchRuleType = object.get("type").toString();
            SearchRuleType type = SearchRuleType
                .getSearchRuleTypeByString(searchRuleType.toLowerCase());
            RawSearchRule rawSearchRule = getRawSearchRule(object, searchRuleType, type);
            rawSearchRuleList.add(rawSearchRule);
            logger.info("Add SearchRule ='" + rawSearchRule + "'");
        }

        return rawSearchRuleList;
    }

    private RawSearchRule getRawSearchRule(JSONObject object, String searchRuleType,
                                           SearchRuleType type) {
        RawSearchRule rawSearchRule;
        if (type == null) {
            rawSearchRule = new RawSearchRule(object, null, null, null);
            rawSearchRule.setValidationResults(Collections.singletonList(
                new ValidationResult(false,
                    "Attribute 'type' = '" + searchRuleType + "' is not supported in "
                        + rawSearchRule)));
        } else {
            SearchRuleGroup group = PropertyLoader.searchRuleGroups.getGroupByType(type);
            Schema schema = PropertyLoader.searchRuleGroupsScheme.getSchema(group.getName());
            rawSearchRule = new RawSearchRule(object, type, group, schema);
        }
        return rawSearchRule;
    }

    public List<RawSearchRule> getComplexInnerRawSearchRules(RawSearchRule parent) {
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

        return innerRawSearchRules;
    }

    public List<RawSearchRule> getFormInnerRawSearchRules(RawSearchRule parent) {
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

        return innerRawSearchRules;
    }
}
