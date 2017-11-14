package com.epam.page.object.generator.parser;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.containers.SearchRulesContainer;
import com.epam.page.object.generator.model.SearchRule;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JSONIntoRuleParserTest {

    File jsonFile = new File("src/test/resources/button.json");

    SearchRulesContainer container;

    @Mock
    ObjectMapper mapper;

    JsonRuleMapper sut;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        sut = new JsonRuleMapper(jsonFile, mapper);

        SearchRule rule = new SearchRule();
        container = new SearchRulesContainer();

        container.setSearchRules(Arrays.asList(rule));
    }

    @Test
    public void getRulesFromJsonTest_success() throws Exception {
        when(mapper.readValue(any(File.class), eq(SearchRulesContainer.class))).thenReturn(container);

        List<SearchRule> list = sut.getRulesFromJSON();

        Assert.assertEquals(container.getSearchRules(), list);
    }

    @Test(expected = IOException.class)
    public void getRulesFromJsonTest_exceptionThrown() throws Exception {
        when(mapper.readValue(any(File.class), eq(SearchRulesContainer.class))).thenThrow(IOException.class);

        sut.getRulesFromJSON();
    }
}
