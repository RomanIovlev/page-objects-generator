package com.epam.page.object.generator.integration;

import com.epam.page.object.generator.PageObjectGeneratorFactory;
import com.epam.page.object.generator.PageObjectsGenerator;
import com.epam.page.object.generator.error.NotValidUrlException;
import com.epam.page.object.generator.error.ValidationException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
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

    private PageObjectsGenerator pog;

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(outputDir + packageName));
        pog = PageObjectGeneratorFactory.getPageObjectGenerator(packageName, "/groups.json", true);
    }

    @Test
    public void pageObjectsGenerator_GenerateCommonSearchRule_Success() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/button.json", outputDir,
            Collections.singletonList("https://www.google.com"));
    }

    @Test
    public void pageObjectGenerator_GenerateCommonSearchRuleWithUniqueness_Success()
        throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/epam-buttons.json", outputDir,
            Collections.singletonList("https://www.epam.com"));
    }

    @Test
    public void pageObjectsGenerator_GenerateComplexSearchRule_Success() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/howto/howto_js_dropdown.asp"));
    }

    @Test
    public void pageObjectsGenerator_GenerateComplexSearchRuleWithInnerElements_Success()
        throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-inner-root.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));
    }

    @Test
    public void pageObjectGenerator_GenerateFormSearchRule_Success() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/form.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/html/html_forms.asp"));
    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_NotSectionAttribute_Exception() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/form-wrong-section.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/html/html_forms.asp"));
    }

    @Test(expected = NotValidUrlException.class)
    public void pageObjectsGenerator_WrongUrl_Exception() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown.json", outputDir,
            Collections.singletonList("https://www.w3schoolsd.com/howto/howto_js_dropdown.asp"));
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_WrongType_Exception() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-wrong-type.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/howto/howto_js_dropdown.asp"));
    }

    @Test(expected = ValidationException.class)
    public void pageObjectsGenerator_ForceFileGenerate_Exception() throws Exception {
        pog.setForceGenerateFile(true);
        pog.generatePageObjects("/dropdown-wrong-type.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/howto/howto_js_dropdown.asp"));
    }

    @Test
    public void pageObjectsGenerator_WrongSelector_Success() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-wrong-selector.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/howto/howto_js_dropdown.asp"));
    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_NotExistenceRootTitle_Exception() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-without-root.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));

    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_DuplicateInnerRoot_Exception() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-duplicate-root.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));
    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_NotExistenceTitleIntoInnerSearchRule_Exception() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-wrong-title.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));

    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_NotUniquenessAttributeIntoRoot_Exception() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-root-without-uniqueness.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));

    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_DuplicateUniquenessAttributeIntoRule_Exception() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-duplicate-uniqueness.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));
    }

    @Test(expected = ValidationException.class)
    public void pageObjectGenerator_NotExistUniquenessAttribute_Exception() throws Exception {
        pog.setForceGenerateFile(true);
        pog.generatePageObjects("/epam-buttons-wrong-uniqueness.json", outputDir,
            Collections.singletonList("https://www.epam.com"));
    }
}