package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.error.NotValidUrlException;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPagesBuilder {

    public List<WebPage> generate(List<String> urls,
                                  SearchRuleExtractor searchRuleExtractor) {
        List<String> invalidUrls = new ArrayList<>();
        List<WebPage> webPages = new ArrayList<>();

        for (String url : urls) {
            try {
                URI uri = new URI(url);
                Connection connect = Jsoup.connect(url);
                Document document = connect.get();
                webPages.add(new WebPage(uri, document, searchRuleExtractor));
            } catch (IOException | URISyntaxException | IllegalArgumentException e) {
                invalidUrls.add("Not valid url: " + url);
            }
        }
        if (!invalidUrls.isEmpty()) {
            throw new NotValidUrlException(invalidUrls);
        }

        return webPages;
    }
}
