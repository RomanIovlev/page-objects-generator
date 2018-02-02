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
    public void generatePageObjects_ButtonJson_StatusOk() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/button.json", outputDir,
            Collections.singletonList("https://www.google.com"));
    }

    @Test
    public void generatePageObjects_EpamButtonsJson_StatusOk()
        throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/epam-buttons.json", outputDir,
            Collections.singletonList("https://www.epam.com"));
    }

    @Test
    public void generatePageObjects_DropDownJson_StatusOk() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/howto/howto_js_dropdown.asp"));
    }

    @Test
    public void generatePageObjects_DropDownInnerRootJson_StatusOk() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-inner-root.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));
    }

    @Test
    public void generatePageObjects_FormJson_StatusOk() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/form.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/html/html_forms.asp"));
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_FormWrongSectionJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/form-wrong-section.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/html/html_forms.asp"));
    }

    @Test(expected = NotValidUrlException.class)
    public void generatePageObjects_DropDownJson_NotValidUrlException() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown.json", outputDir,
            Collections.singletonList("https://www.w3schoolsd.com/howto/howto_js_dropdown.asp"));
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_DropDownWrongTypeJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-wrong-type.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/howto/howto_js_dropdown.asp"));
    }

    @Test(expected = ValidationException.class)
    public void forceGeneratePageObjects_DropDownWrongTypeJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(true);
        pog.generatePageObjects("/dropdown-wrong-type.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/howto/howto_js_dropdown.asp"));
    }

    @Test
    public void generatePageObjects_DropDownWrongSelectorJson_StatusOk() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-wrong-selector.json", outputDir,
            Collections.singletonList("https://www.w3schools.com/howto/howto_js_dropdown.asp"));
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_DropDownWithoutRootJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-without-root.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));

    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_DropDownDuplicateRootJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-duplicate-root.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));
    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_DropDownWrongTitleJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-wrong-title.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));

    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_DropDownRootWithoutUniquenessJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-root-without-uniqueness.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));

    }

    @Test(expected = ValidationException.class)
    public void generatePageObjects_DropDownDuplicateUniquenessJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/dropdown-duplicate-uniqueness.json", outputDir,
            Collections.singletonList("http://materializecss.com/dropdown.html"));
    }

    @Test(expected = ValidationException.class)
    public void generate_EpamButtonsWrongUniquenessJson_ValidationException() throws Exception {
        pog.setForceGenerateFile(true);
        pog.generatePageObjects("/epam-buttons-wrong-uniqueness.json", outputDir,
            Collections.singletonList("https://www.epam.com"));
    }
}