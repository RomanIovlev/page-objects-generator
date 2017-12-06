package com.epam.page.object.generator.integration;

import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.builders.JavaClassBuilder;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.errors.NotValidUrlException;
import com.epam.page.object.generator.errors.ValidationException;
import com.epam.page.object.generator.builders.WebPagesBuilder;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.TypeTransformer;
import com.epam.page.object.generator.utils.ValidationChecker;
import com.epam.page.object.generator.validators.JsonSchemaValidator;
import com.epam.page.object.generator.validators.JsonValidators;
import com.epam.page.object.generator.validators.ValidationExceptionConverter;
import com.epam.page.object.generator.validators.WebValidators;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
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
    private ArrayList<String> urls = new ArrayList<>();

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(outputDir + packageName));
    }

    private PageObjectsGenerator initPog(String url, boolean forceGenerateFiles)
        throws IOException {
        urls.add(url);

        SupportedTypesContainer bc = new SupportedTypesContainer();

        RawSearchRuleMapper parser = new RawSearchRuleMapper();

        JsonSchemaValidator validator = new JsonSchemaValidator(new ValidationExceptionConverter());

        TypeTransformer transformer = new TypeTransformer(bc);

        ValidationChecker checker = new ValidationChecker();

        JavaFileWriter javaPoetAdapter = new JavaFileWriter();

        JsonValidators jsonValidators = new JsonValidators();

        WebValidators webValidators = new WebValidators();

        JavaClassBuilder javaClassBuilder = new JavaClassBuilder(packageName);

        WebElementGroupFieldBuilder webElementGroupFieldBuilder = new WebElementGroupFieldBuilder();

        WebPagesBuilder builder = new WebPagesBuilder();

        PageObjectsGenerator pog = new PageObjectsGenerator(parser, validator, transformer, checker,
            jsonValidators, webValidators, javaClassBuilder, webElementGroupFieldBuilder, javaPoetAdapter, builder);

        pog.setForceGenerateFile(forceGenerateFiles);

        return pog;
    }

    @Test
    public void pageObjectsGenerator_SuccessGenerateCommonSearchRule() throws Exception {
        PageObjectsGenerator pog = initPog(
            "https://www.google.com",
            false);

        pog.generatePageObjects("/button.json", outputDir, urls);
    }

    @Test
    public void pageObjectsGenerator_SuccessGenerateComplexSearchRule() throws Exception {
        PageObjectsGenerator pog = initPog(
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            false);

        pog.generatePageObjects("/dropdown.json", outputDir, urls);
    }


    @Test
    public void pageObjectsGenerator_SuccessGenerateComplexSearchRuleWithInnerElements() throws Exception {
        PageObjectsGenerator pog = initPog(
            "http://materializecss.com/dropdown.html",
            false);

        pog.generatePageObjects("/dropdown-inner-root.json", outputDir, urls);
    }

    @Test
    public void pageObjectGenerator_SuccessGenerateFormSearchRule() throws Exception {
        PageObjectsGenerator pog = initPog(
            "https://www.w3schools.com/html/html_forms.asp",
            false);

        pog.generatePageObjects("/form.json", outputDir, urls);
    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_NotSectionAttribute() throws Exception {
        PageObjectsGenerator pog = initPog(
            "https://www.w3schools.com/html/html_forms.asp",
            false);

        pog.generatePageObjects("/form-wrong-section.json", outputDir, urls);
    }

    @Test(expected = NotValidUrlException.class)
    public void pageObjectsGenerator_wrongUrl() throws Exception {

        PageObjectsGenerator pog = initPog(
            "https://www.w3schoolsd.com/howto/howto_js_dropdown.asp",
            false);

        pog.generatePageObjects("/dropdown.json", outputDir, urls);
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_wrongType() throws Exception {
        PageObjectsGenerator pog = initPog(
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            false);

        pog.generatePageObjects("/dropdown-wrong-type.json", outputDir, urls);
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_forceFileGenerate() throws Exception {
        PageObjectsGenerator pog = initPog(
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            true);

        pog.generatePageObjects("/dropdown-wrong-type.json", outputDir, urls);
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_wrongSelector() throws Exception {
        PageObjectsGenerator pog = initPog(
            "https://www.w3schools.com/howto/howto_js_dropdown.asp",
            false);

        pog.generatePageObjects("/dropdown-wrong-selector.json", outputDir, urls);
    }


}