package com.epam.page.object.generator.integration;

import com.epam.page.object.generator.PageObjectGeneratorFactory;
import com.epam.page.object.generator.PageObjectsGenerator;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class OfflineTest {

    private String outputDir = "src/test/resources/";
    private String packageName = "test";

    private PageObjectsGenerator pog;

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(outputDir + packageName));
        pog = PageObjectGeneratorFactory
            .getPageObjectGenerator(packageName, "/groups.json", false);
    }

    @Test
    public void generate_CalculateJson_StatusOk() throws Exception {
        pog.setForceGenerateFile(false);
        pog.generatePageObjects("/jsonForBuilders/webPage/jdi/calculate.json", outputDir,
            Collections.singletonList("/jsonForBuilders/webPage/jdi/test.html"));
    }
}