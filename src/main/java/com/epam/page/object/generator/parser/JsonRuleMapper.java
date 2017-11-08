package com.epam.page.object.generator.parser;

import com.epam.page.object.generator.containers.SearchRulesContainer;
import com.epam.page.object.generator.model.SearchRule;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonRuleMapper {

    private File file;
    ObjectMapper objectMapper;

    public JsonRuleMapper(File file, ObjectMapper objectMapper) {
        this.file = file;
        this.objectMapper = objectMapper;
    }

    /**
     * Parsing searching rules from JSON file.
     *
     * @return List of search rules from JSON file.
     * @throws IOException If can't open JSON file.
     */
    public List<SearchRule> getRulesFromJSON() throws IOException {

        SearchRulesContainer elements = objectMapper.readValue(file, SearchRulesContainer.class);
//        TODO: fix mapping of "title" field

        return elements.getSearchRules();
    }

}