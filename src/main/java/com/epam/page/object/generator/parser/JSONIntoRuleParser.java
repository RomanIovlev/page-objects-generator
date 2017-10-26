package com.epam.page.object.generator.parser;

import com.epam.page.object.generator.containers.SearchRulesContainer;
import com.epam.page.object.generator.model.SearchRule;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.ParseException;

public class JSONIntoRuleParser {

    private String jsonPath;
    private Set<String> supportedTypes;

    public JSONIntoRuleParser(String jsonPath, Set<String> supportedTypes) {
        this.jsonPath = jsonPath;
        this.supportedTypes = supportedTypes;
    }

    /**
     * Parsing searching rules from JSON file.
     *
     * @return List of search rules from JSON file.
     * @throws IOException If can't open JSON file.
     * @throws ParseException If JSON has invalid format.
     */
    public List<SearchRule> getRulesFromJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchRulesContainer elements = objectMapper.readValue(new File(jsonPath), SearchRulesContainer.class);

        return elements.getSearchRules();
    }

    public Set<String> getSupportedTypes() {
        return supportedTypes;
    }

    public void setSupportedTypes(Set<String> supportedTypes) {
        this.supportedTypes = supportedTypes;
    }

}