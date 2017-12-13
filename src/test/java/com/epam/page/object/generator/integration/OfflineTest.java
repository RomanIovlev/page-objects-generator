package com.epam.page.object.generator.integration;

import com.epam.page.object.generator.testDataBuilders.PageObjectGeneratorTestDataBuilder;
import com.epam.page.object.generator.testDataBuilders.PageObjectGeneratorTestDataBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class OfflineTest {

    private String outputDir = "src/test/resources/";
    private String packageName = "test";

    private PageObjectGeneratorTestDataBuilder pog;

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(outputDir + packageName));
        pog = PageObjectGeneratorTestDataBuilderFactory
            .getPageObjectGenerator(packageName, "/groups.json");
    }

    @Test
    public void pageObjectsGenerator_SuccessGenerateCommonSearchRule() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/jsonForBuilders/webPage/jdi/calculate.json", outputDir,
            Collections.singletonList("/jsonForBuilders/webPage/jdi/test.html"));
    }
}