package com.epam.page.object.generator.testDataBuilders;

import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPageTestDataBuilder {

    private static SearchRuleExtractor extractor = new SearchRuleExtractor();

    public static WebPage getWebPage(String filePath) {
        String html = null;
        try {
            html = Files
                .toString(new File(WebPageTestDataBuilder.class.getResource(filePath).getFile()),
                    Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(html);

        return new WebPage(null, doc, extractor);
    }
}
