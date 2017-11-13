package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class JavaPoetAdapterTest {

    private JavaPoetAdapter sut;

    private static final String OUTPUT_DIR = "src/test/resources/";
    private static final String PACKAGE_NAME = "test";
    private static final String TEST_URL = "https://www.google.com";

    @Before
    public void setUp() throws Exception {
        sut = new JavaPoetAdapter(new SupportedTypesContainer(), new XpathToCssTransformation());
    }

    @Test
    public void writeFile() throws Exception {
        sut.writeFile(
                PACKAGE_NAME,
                OUTPUT_DIR,
                Lists.newArrayList(),
                Lists.newArrayList(TEST_URL)
        );

        assertTrue((new File(OUTPUT_DIR + PACKAGE_NAME + "/page")).exists());
        assertTrue((new File(OUTPUT_DIR + PACKAGE_NAME + "/site")).exists());
    }

}