package com.epam.page.object.generator.util;

import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.validator.ValidationResult;
import java.io.IOException;
import java.io.InputStream;
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

    private SearchRuleGroupsScheme searchRuleGroupsScheme;
    private SearchRuleGroups searchRuleGroups;

    public RawSearchRuleMapper(SearchRuleGroupsScheme searchRuleGroupsScheme,
                               SearchRuleGroups searchRuleGroups) {
        this.searchRuleGroupsScheme = searchRuleGroupsScheme;
        this.searchRuleGroups = searchRuleGroups;
    }

    public List<RawSearchRule> getRawSearchRuleList(String json) {
        List<RawSearchRule> rawSearchRuleList = new ArrayList<>();

        try (InputStream jsonStream = RawSearchRuleMapper.class.getResourceAsStream(json)) {
            JSONObject tree = new JSONObject(
                new JSONTokener(jsonStream));
            JSONArray elements = tree.getJSONArray("elements");
            for (int i = 0; i < elements.length(); i++) {
                JSONObject object = elements.getJSONObject(i);
                String searchRuleType = object.get("type").toString();
                SearchRuleType type = SearchRuleType
                    .getSearchRuleTypeByString(searchRuleType.toLowerCase());
                RawSearchRule rawSearchRule = getRawSearchRule(object, searchRuleType, type);
                rawSearchRuleList.add(rawSearchRule);
                logger.debug("Add SearchRule ='" + rawSearchRule + "'");
            }
        } catch (IOException e) {
            throw new NullPointerException("Failed reading property file = '" + json + "'!");
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
            SearchRuleGroup group = searchRuleGroups.getGroupByType(type);
            Schema schema = searchRuleGroupsScheme.getSchema(group.getName());
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
            Schema schema = searchRuleGroupsScheme.getSchema("complexInnerSearchRule");
            RawSearchRule rawSearchRule = new RawSearchRule(object, type, group, schema);
            innerRawSearchRules.add(rawSearchRule);
            logger.debug("Add complexInnerSearchRule ='" + rawSearchRule + "'");
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
            Schema schema = searchRuleGroupsScheme.getSchema("formInnerSearchRule");
            RawSearchRule rawSearchRule = new RawSearchRule(object, type, group, schema);
            innerRawSearchRules.add(rawSearchRule);
            logger.debug("Add formInnerSearchRule ='" + rawSearchRule + "'");
        }

        return innerRawSearchRules;
    }
}
