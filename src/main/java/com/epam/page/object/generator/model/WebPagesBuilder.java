package com.epam.page.object.generator.model;

import com.epam.page.object.generator.errors.NotValidUrlException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;

public class WebPagesBuilder {


    public List<WebPage> generate(List<String> urls) {
        StringBuilder invalidUrls = new StringBuilder();
        List<WebPage> webPages = new ArrayList<>();

        for (String url : urls) {
            try {
                webPages.add( new WebPage(new URI(url), Jsoup.connect(url).get()));
            } catch (IOException | URISyntaxException e) {
                invalidUrls.append("\n Not valid url: ").append(url);
            }
        }
        if (invalidUrls.length() > 0){
            throw new NotValidUrlException(String.valueOf(invalidUrls));
        }

        return webPages;
    }

}
