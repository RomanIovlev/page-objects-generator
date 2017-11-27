package com.epam.page.object.generator.model;

import com.epam.page.object.generator.errors.WebPageBuilderException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPageBuilder {
    private Document document;
    private URI uri;

    public WebPageBuilder() {}

    public WebPage buildWebPage(String url) throws WebPageBuilderException {
        try {
            document = Jsoup.connect(url).get();
            uri = new URI(url);
        }
        catch (IOException | URISyntaxException e){
         throw new WebPageBuilderException("Inputted url: " + url + " is not valid!");
        }



        return new WebPage(uri, document);
    }
}
