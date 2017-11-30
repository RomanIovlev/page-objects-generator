package com.epam.page.object.generator.utils.searchRuleScheme;

import java.util.Map;
import org.everit.json.schema.Schema;

public class SearchRuleGroupsScheme {

    private Map<String, Schema> searchRuleSchemas;

    public SearchRuleGroupsScheme(
        Map<String, Schema> searchRuleSchemas) {
        this.searchRuleSchemas = searchRuleSchemas;
    }

    public Map<String, Schema> getSearchRuleSchemas() {
        return searchRuleSchemas;
    }

    public Schema getSchema(String groupName){
        return searchRuleSchemas.get(groupName);
    }
}
