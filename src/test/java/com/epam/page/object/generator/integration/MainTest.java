package com.epam.page.object.generator.integration;

import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.errors.NotValidUrlException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.parser.JsonRuleMapper;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.epam.page.object.generator.validators.ValidatorsStarter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Each test generate it's own Java objects for JDI. Probably it would be better to run each test in
 * isolation from others
 */
@Ignore
public class MainTest {

    private String outputDir = "src/test/resources/";
    private String packageName = "test";

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(outputDir + packageName));
    }

    private PageObjectsGenerator initPog(String jsonPath, String url,
                                         boolean checkLocatorUniqueness,
                                         boolean forceGenerateFiles) throws IOException {
        List<String> urls = new ArrayList<>();

        urls.add(url);

        SupportedTypesContainer bc = new SupportedTypesContainer();

        JsonRuleMapper parser = new JsonRuleMapper(new File(jsonPath), new ObjectMapper());

        XpathToCssTransformation xpathToCssTransformation = new XpathToCssTransformation();

        JavaFileWriter javaPoetAdapter = new JavaFileWriter(bc, xpathToCssTransformation);

        ValidatorsStarter validatorsStarter = new ValidatorsStarter(bc);
        validatorsStarter.setCheckLocatorsUniqueness(checkLocatorUniqueness);

        PageObjectsGenerator pog = new PageObjectsGenerator(parser, validatorsStarter,
            javaPoetAdapter, outputDir, urls, packageName);

        pog.setForceGenerateFile(forceGenerateFiles);

        return pog;
    }

    @Test
    public void pageObjectsGenerator_success() throws Exception {

        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown.json",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            true,
            false);

        pog.generatePageObjects();
    }

    @Test
    public void pageObjectGenerator_FormSuccess() throws Exception {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/form.json",
            "https://www.w3schools.com/html/html_forms.asp",
            true,
            false
        );

        pog.generatePageObjects();
    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_NotSectionAttribute() throws Exception {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/form-wrong-section.json",
            "https://www.w3schools.com/html/html_forms.asp",
            true,
            false);

        pog.generatePageObjects();
    }

    @Test(expected = NotValidUrlException.class)
    public void pageObjectsGenerator_wrongUrl() throws Exception {

        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown.json",
            "https://www.w3schoolsd.com/howto/howto_js_dropdown.asp",
            true,
            false);

        pog.generatePageObjects();
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_wrongType() throws Exception {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown-wrong-type.json",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            true,
            false);

        pog.generatePageObjects();
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_forceFileGenerate() throws Exception {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown-wrong-type.json",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            true,
            true);

        pog.generatePageObjects();
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_wrongSelector() throws Exception {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown-wrong-selector.json",
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            true,
            false);

        pog.generatePageObjects();
    }

    @Test(expected = ValidationException.class)
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

    @Test
    public void pageObjectsGenerator_GenerateDropdownElementWithInnerElements() throws Exception {
        PageObjectsGenerator pog = initPog(
            "src/test/resources/dropdown-inner-root.json",
            "http://materializecss.com/dropdown.html",
            false,
            false);

        pog.generatePageObjects();
    }

}