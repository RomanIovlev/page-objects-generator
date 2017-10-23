package com.epam.page.object.generator.parser;

import static java.lang.String.format;

import com.epam.page.object.generator.SearchRulesContainer;
import com.epam.page.object.generator.model.SearchRule;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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
    public List<SearchRule> getRulesFromJSON() throws IOException, ParseException {

        ObjectMapper objectMapper = new ObjectMapper();
        SearchRulesContainer elements = objectMapper.readValue(new File(jsonPath),SearchRulesContainer.class);

        List<SearchRule> searchRules = elements.getSearchRules();

        for (Iterator<SearchRule> iter = searchRules.iterator(); iter.hasNext();) {
            String type = iter.next().getType().toLowerCase();
            if (!supportedTypes.contains(type)) {
                iter.remove();

                throw new ParseException(1,
                    format("Unsupported element type '%s'. Supported types: %s",
                        type, supportedTypes));
                }
            }

            return searchRules;
        }

    public Set<String> getSupportedTypes() {
        return supportedTypes;
    }

    public void setSupportedTypes(Set<String> supportedTypes) {
        this.supportedTypes = supportedTypes;
    }
}