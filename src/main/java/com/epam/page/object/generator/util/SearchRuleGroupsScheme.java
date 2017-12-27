package com.epam.page.object.generator.util;

import java.util.Map;
import org.everit.json.schema.Schema;

/**
 * This class uses by JSON scheme validator and contains information about schemes uses for
 * validations groups founded in JSON file.
 */
public class SearchRuleGroupsScheme {

    private Map<String, Schema> searchRuleSchemas;

    public SearchRuleGroupsScheme(
        Map<String, Schema> searchRuleSchemas) {
        this.searchRuleSchemas = searchRuleSchemas;
    }

    /**
     * Find required scheme by group name
     *
     * @param groupName which we search for the group scheme
     * @return json schema {@link Schema}
     */
    public Schema getSchema(String groupName) {
        return searchRuleSchemas.get(groupName);
    }
}
