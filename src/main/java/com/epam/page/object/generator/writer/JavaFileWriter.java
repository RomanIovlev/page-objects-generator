package com.epam.page.object.generator.writer;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface JavaFileWriter {

    void writeFile(String packageName, String outputDir, List<SearchRule> searchRules,
                   List<String> urls)
        throws IOException, URISyntaxException, XpathToCssTransformerException;

}