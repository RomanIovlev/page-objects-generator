package com.epam.page.object.generator.integrationalTests;

import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.builder.FieldAnnotationFactory;
import com.epam.page.object.generator.builder.SiteFieldSpecBuilder;
import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.errors.NotUniqueSelectorsException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.parser.JSONIntoRuleParser;
import com.epam.page.object.generator.validators.SearchRuleValidator;
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

    String outputDir = "src/test/resources/";
    String packageName = "test";

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

        FieldAnnotationFactory fieldAnnotationFactory = new FieldAnnotationFactory();
        BuildersContainer bc = new BuildersContainer(fieldAnnotationFactory);
        JSONIntoRuleParser parser = new JSONIntoRuleParser(new File(jsonPath), new ObjectMapper());
        SearchRuleValidator validator = new SearchRuleValidator(bc.getSupportedTypes());

        validator.setCheckLocatorsUniqueness(checkLocatorUniqueness);

        SiteFieldSpecBuilder siteFieldSpecBuilder = new SiteFieldSpecBuilder(packageName, bc,
            fileWriter);
        PageObjectsGenerator pog = new PageObjectsGenerator(parser, fileWriter,
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