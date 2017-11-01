package com.epam.page.object.generator.integrationalTests;

import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.builder.FieldAnnotationFactory;
import com.epam.page.object.generator.builder.SiteFieldSpecBuilder;
import com.epam.page.object.generator.containers.BuildersContainer;
import com.epam.page.object.generator.parser.JSONIntoRuleParser;
import com.epam.page.object.generator.validators.SearchRuleValidator;
import com.epam.page.object.generator.writer.JavaFileWriter;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

    private String outputDir = "src/test/resources/";
    private String packageName = "test";

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(outputDir + packageName));
    }

    @Test
    public void pageObjectsGenerator_ok() throws IOException, URISyntaxException {
        List<String> urls = new ArrayList<>();

        urls.add("https://www.w3schools.com/howto/howto_js_dropdown.asp");

        String jsonPath = "src/test/resources/dropdown.json";
        JavaFileWriter fileWriter = new JavaFileWriter(outputDir);
        FieldAnnotationFactory fieldAnnotationFactory = new FieldAnnotationFactory();
        BuildersContainer bc = new BuildersContainer(fieldAnnotationFactory);
        JSONIntoRuleParser parser = new JSONIntoRuleParser(new File(jsonPath));
        SearchRuleValidator validator = new SearchRuleValidator(bc.getSupportedTypes());

        validator.setCheckLocatorsUniqueness(true);

        SiteFieldSpecBuilder siteFieldSpecBuilder = new SiteFieldSpecBuilder(packageName, bc,
            fileWriter);
        PageObjectsGenerator pog = new PageObjectsGenerator(parser, fileWriter,
            siteFieldSpecBuilder, validator, urls, packageName);

        pog.generatePageObjects();
    }

    @Test
    public void pageObjectsGenerator_wrongType() throws IOException, URISyntaxException {
        List<String> urls = new ArrayList<>();

        urls.add("https://www.w3schools.com/howto/howto_js_dropdown.asp");

        String outputDir = "src/test/resources/";
        String packageName = "test";

        String jsonPath = "src/test/resources/dropdown-wrong-type.json";
        JavaFileWriter fileWriter = new JavaFileWriter(outputDir);
        FieldAnnotationFactory fieldAnnotationFactory = new FieldAnnotationFactory();
        BuildersContainer bc = new BuildersContainer(fieldAnnotationFactory);
        JSONIntoRuleParser parser = new JSONIntoRuleParser(new File(jsonPath));
        SearchRuleValidator validator = new SearchRuleValidator(bc.getSupportedTypes());

        validator.setCheckLocatorsUniqueness(true);

        SiteFieldSpecBuilder siteFieldSpecBuilder = new SiteFieldSpecBuilder(packageName, bc,
                fileWriter);
        PageObjectsGenerator pog = new PageObjectsGenerator(parser, fileWriter,
                siteFieldSpecBuilder, validator, urls, packageName);

        pog.generatePageObjects();
    }

    @Test
    public void pageObjectsGenerator_forceFileGenerate() throws IOException, URISyntaxException {
        List<String> urls = new ArrayList<>();

        urls.add("https://www.w3schools.com/howto/howto_js_dropdown.asp");

        String outputDir = "src/test/resources/";
        String packageName = "test";

        String jsonPath = "src/test/resources/dropdown-wrong-type.json";
        JavaFileWriter fileWriter = new JavaFileWriter(outputDir);
        FieldAnnotationFactory fieldAnnotationFactory = new FieldAnnotationFactory();
        BuildersContainer bc = new BuildersContainer(fieldAnnotationFactory);
        JSONIntoRuleParser parser = new JSONIntoRuleParser(new File(jsonPath));
        SearchRuleValidator validator = new SearchRuleValidator(bc.getSupportedTypes());

        validator.setCheckLocatorsUniqueness(true);

        SiteFieldSpecBuilder siteFieldSpecBuilder = new SiteFieldSpecBuilder(packageName, bc,
                fileWriter);
        PageObjectsGenerator pog = new PageObjectsGenerator(parser, fileWriter,
                siteFieldSpecBuilder, validator, urls, packageName);

        pog.setForceGenerateFile(true);

        pog.generatePageObjects();
    }

    @Test
    public void pageObjectsGenerator_UniquenessTrue() throws IOException, URISyntaxException {
        List<String> urls = new ArrayList<>();

        urls.add("https://www.google.com");

        String outputDir = "src/test/resources/";
        String packageName = "test";

        String jsonPath = "src/test/resources/button.json";
        JavaFileWriter fileWriter = new JavaFileWriter(outputDir);
        FieldAnnotationFactory fieldAnnotationFactory = new FieldAnnotationFactory();
        BuildersContainer bc = new BuildersContainer(fieldAnnotationFactory);
        JSONIntoRuleParser parser = new JSONIntoRuleParser(new File(jsonPath));
        SearchRuleValidator validator = new SearchRuleValidator(bc.getSupportedTypes());

        validator.setCheckLocatorsUniqueness(true);

        SiteFieldSpecBuilder siteFieldSpecBuilder = new SiteFieldSpecBuilder(packageName, bc,
                fileWriter);
        PageObjectsGenerator pog = new PageObjectsGenerator(parser, fileWriter,
                siteFieldSpecBuilder, validator, urls, packageName);

        pog.generatePageObjects();
    }

    @Test
    public void pageObjectsGenerator_UniquenessFalse() throws IOException, URISyntaxException {
        List<String> urls = new ArrayList<>();

        urls.add("https://www.google.com");

        String outputDir = "src/test/resources/";
        String packageName = "test";

        String jsonPath = "src/test/resources/button.json";
        JavaFileWriter fileWriter = new JavaFileWriter(outputDir);
        FieldAnnotationFactory fieldAnnotationFactory = new FieldAnnotationFactory();
        BuildersContainer bc = new BuildersContainer(fieldAnnotationFactory);
        JSONIntoRuleParser parser = new JSONIntoRuleParser(new File(jsonPath));
        SearchRuleValidator validator = new SearchRuleValidator(bc.getSupportedTypes());

        validator.setCheckLocatorsUniqueness(false);

        SiteFieldSpecBuilder siteFieldSpecBuilder = new SiteFieldSpecBuilder(packageName, bc,
                fileWriter);
        PageObjectsGenerator pog = new PageObjectsGenerator(parser, fileWriter,
                siteFieldSpecBuilder, validator, urls, packageName);

        pog.generatePageObjects();
    }
}