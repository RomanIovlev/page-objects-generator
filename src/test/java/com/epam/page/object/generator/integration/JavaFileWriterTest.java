package com.epam.page.object.generator.integration;

import static org.junit.Assert.assertTrue;

import com.epam.page.object.generator.adapter.JavaFileWriter;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.model.WebPagesBuilder;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class JavaFileWriterTest {

    private static final String OUTPUT_DIR = "src/test/resources/";
    private static final String PACKAGE_NAME = "test";
    private static final String TEST_URL = "http://www.google.com";
    private JavaFileWriter sut;
    private List<WebPage> webPages;

//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        sut = new JavaFileWriter(new SupportedTypesContainer());
//        WebPagesBuilder webPagesBuilder = new WebPagesBuilder();
//        webPages = webPagesBuilder.generate(Collections.singletonList(TEST_URL));
//
//        FileUtils.deleteDirectory(new File(OUTPUT_DIR + PACKAGE_NAME));
//    }
//
//    @Test
//    public void writeFile() throws Exception {
//        sut.writeFiles(
//            OUTPUT_DIR,
//            PACKAGE_NAME,
//            webPages
//        );
//
//        assertTrue((new File(OUTPUT_DIR + PACKAGE_NAME + "/page")).exists());
//        assertTrue((new File(OUTPUT_DIR + PACKAGE_NAME + "/site")).exists());
//    }

}