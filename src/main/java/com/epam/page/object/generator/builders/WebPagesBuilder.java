package com.epam.page.object.generator.builders;

import com.epam.page.object.generator.errors.NotValidUrlException;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
                invalidUrls.add("\nNot valid url: " + url);
            }
        }
        if (!invalidUrls.isEmpty()) {
            throw new NotValidUrlException(invalidUrls.stream().collect(Collectors.joining("")));
        }

        return webPages;
    }
}
