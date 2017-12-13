package com.epam.page.object.generator.util;

import java.util.Map;
import org.everit.json.schema.Schema;

public class SearchRuleGroupsScheme {

    private Map<String, Schema> searchRuleSchemas;

    public SearchRuleGroupsScheme(
        Map<String, Schema> searchRuleSchemas) {
        this.searchRuleSchemas = searchRuleSchemas;
    }

    public Schema getSchema(String groupName) {
        return searchRuleSchemas.get(groupName);
    }
}
