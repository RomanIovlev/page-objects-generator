package com.epam.page.object.generator.builders;

import com.epam.page.object.generator.errors.NotValidUrlException;
import com.epam.page.object.generator.model.WebPage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPagesBuilder {

    public List<WebPage> generate(List<String> urls) {
        StringBuilder invalidUrls = new StringBuilder();
        List<WebPage> webPages = new ArrayList<>();

        for (String url : urls) {
            try {
                URI uri = new URI(url);
                Connection connect = Jsoup.connect(url);
                Document document = connect.get();
                webPages.add(new WebPage(uri, document));
            } catch (IOException | URISyntaxException | IllegalArgumentException e) {
                invalidUrls.append("\n Not valid url: ").append(url);
            }
        }
        if (invalidUrls.length() > 0) {
            throw new NotValidUrlException(String.valueOf(invalidUrls));
        }

        return webPages;
    }
}
