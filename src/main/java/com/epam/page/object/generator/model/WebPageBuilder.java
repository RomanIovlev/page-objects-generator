package com.epam.page.object.generator.model;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPageBuilder {

    public WebPageBuilder() {

    }

    public WebPage buildWebPage(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        return new WebPage(url, document);
    }
}
