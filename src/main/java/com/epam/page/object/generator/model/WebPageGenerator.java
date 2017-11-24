package com.epam.page.object.generator.model;

import com.epam.page.object.generator.errors.NotValidUrlException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebPageGenerator {

    public List<WebPage> generate(List<String> urls) {
        WebPageBuilder webPageBuilder = new WebPageBuilder();
        StringBuilder invalidUrls = new StringBuilder();
        List<WebPage> webPages = new ArrayList<>();

        for (String url : urls) {
            try {
                webPages.add( webPageBuilder.buildWebPage(url));
            } catch (IOException | IllegalArgumentException e) {
                invalidUrls.append("\n Not valid url: ").append(url);
            }
        }
        if (invalidUrls.length() > 0){
            throw new NotValidUrlException(String.valueOf(invalidUrls));
        }

        return webPages;
    }

}
