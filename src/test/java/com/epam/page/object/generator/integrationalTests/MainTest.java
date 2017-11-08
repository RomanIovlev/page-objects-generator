package com.epam.page.object.generator.integrationalTests;

import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.builder.PageFieldSpecBuilder;
import com.epam.page.object.generator.builder.SiteFieldSpecBuilder;
import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.epam.page.object.generator.validators.ValidationContext;
import com.epam.page.object.generator.writer.JavaFileWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    private String outputDir = "src/test/resources/";
    private String packageName = "test";

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(outputDir + packageName));
    }

    private PageObjectsGenerator initPog(String jsonPath, String url,
                                         boolean checkLocatorUniqueness,
                                         boolean forceGenerateFiles) {
        List<String> urls = new ArrayList<>();

        urls.add(url);

        JavaFileWriter fileWriter = new JavaFileWriter(outputDir);

        BuildersContainer bc = new BuildersContainer();

        JsonRuleMapper parser = new JsonRuleMapper(new File(jsonPath), new ObjectMapper());

        List<SearchRule> rulesFromJSON = parser.getRulesFromJSON();

        ValidationContext validationContext = new ValidationContext(rulesFromJSON, urls);

        SearchRuleValidator validator = new SearchRuleValidator(bc.getSupportedTypes(),
            validationContext);

        validator.setCheckLocatorsUniqueness(checkLocatorUniqueness);

        PageFieldSpecBuilder pageFieldSpecBuilder = new PageFieldSpecBuilder(bc);

        SiteFieldSpecBuilder siteFieldSpecBuilder = new SiteFieldSpecBuilder(packageName);

        PageObjectsGenerator pog = new PageObjectsGenerator(parser, fileWriter,
            pageFieldSpecBuilder,
            siteFieldSpecBuilder, validator, urls, packageName);

        pog.setForceGenerateFile(forceGenerateFiles);

        return pog;
    }

    @Test
    public void pageObjectsGenerator_ok() throws IOException, URISyntaxException {

        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown.json",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            true,
            false);

        pog.generatePageObjects();
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_wrongType() throws IOException, URISyntaxException {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown-wrong-type.json",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            true,
            false);

        pog.generatePageObjects();
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_forceFileGenerate() throws IOException, URISyntaxException {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown-wrong-type.json",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            true,
            true);

        pog.generatePageObjects();
    }

    @Test(expected = NotUniqueSelectorsException.class)
    public void pageObjectsGenerator_NotUniqueLocatorFoundInUniquenessModeOn() throws Exception {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/button.json",
            "https://www.google.com",
            true,
            false);

        pog.generatePageObjects();
    }

    @Test
    public void pageObjectsGenerator_NotUniqueLocatorFoundInUniquenessModeOff() throws Exception {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/button.json",
            "https://www.google.com",
            false,
            false);

        pog.generatePageObjects();
    }

}