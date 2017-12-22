package com.epam.page.object.generator.builder.webpage;

import static org.junit.Assert.*;

import com.epam.page.object.generator.error.NotValidPathsException;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class LocalWebPageBuilderTest {

    private SearchRuleExtractor searchRuleExtractor = new SearchRuleExtractor();
    private LocalWebPageBuilder localWebPageBuilder = new LocalWebPageBuilder();

    private List<String> validPaths = Lists.newArrayList("/jsonForBuilders/webPage/jdi/test.html");
    private List<String> invalidPaths = Lists.newArrayList("/jsonForBuilders/webPage/jdi/testException.html");

    @Test
    public void generate_Success(){
        List<WebPage> webPages = localWebPageBuilder.generate(validPaths, searchRuleExtractor);

        assertNotNull(webPages);
    }

    @Test(expected = NotValidPathsException.class)
    public void generate_FailedFileDoesNotExist(){
        localWebPageBuilder.generate(invalidPaths, searchRuleExtractor);
    }

}