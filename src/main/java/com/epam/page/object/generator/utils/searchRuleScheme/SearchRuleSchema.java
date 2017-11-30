package com.epam.page.object.generator.utils.searchRuleScheme;

public class SearchRuleSchema {

    private String name;
    private String schemaPath;

    public SearchRuleSchema(String name, String schemaPath) {
        this.name = name;
        this.schemaPath = schemaPath;
    }

    public String getName() {
        return name;
    }

    public String getSchemaPath() {
        return schemaPath;
    }
}
