package com.epam.page.object.generator.integration;

import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.google.common.collect.Lists;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class JavaFileWriterTest {

    private static final String OUTPUT_DIR = "src/test/resources/";
    private static final String PACKAGE_NAME = "test";
    private static final String TEST_URL = "https://www.google.com";
    private JavaFileWriter sut;

    @Before
    public void setUp() throws Exception {
        sut = new JavaFileWriter(new SupportedTypesContainer(), new XpathToCssTransformation());
    }

    @Test
    public void writeFile() throws Exception {
        sut.writeFiles(
            OUTPUT_DIR,
            PACKAGE_NAME,
            Lists.newArrayList(),
            Lists.newArrayList(TEST_URL)
        );

        assertTrue((new File(OUTPUT_DIR + PACKAGE_NAME + "/page")).exists());
        assertTrue((new File(OUTPUT_DIR + PACKAGE_NAME + "/site")).exists());
    }

}