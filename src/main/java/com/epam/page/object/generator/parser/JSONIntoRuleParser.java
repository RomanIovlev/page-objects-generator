package com.epam.page.object.generator.parser;

import com.epam.page.object.generator.containers.SearchRulesContainer;
import com.epam.page.object.generator.model.SearchRule;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONIntoRuleParser {

    private File file;

    public JSONIntoRuleParser(File file) {
        this.file = file;
    }

    /**
     * Parsing searching rules from JSON file.
     *
     * @return List of search rules from JSON file.
     * @throws IOException If can't open JSON file.
     */
    public List<SearchRule> getRulesFromJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchRulesContainer elements = objectMapper.readValue(file, SearchRulesContainer.class);

        return elements.getSearchRules();
    }

}