package com.epam.page.object.generator.builder.webpage;

import com.epam.page.object.generator.error.NotValidUrlException;
import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlWebPageBuilder implements WebPageBuilder {

    private final static Logger logger = LoggerFactory.getLogger(UrlWebPageBuilder.class);

    @Override
    public List<WebPage> generate(List<String> paths,
                                  SearchRuleExtractor searchRuleExtractor) {
        List<String> invalidUrls = new ArrayList<>();
        List<WebPage> webPages = new ArrayList<>();

        for (String url : paths) {
            try {
                URI uri = new URI(url);
                Connection connect = Jsoup.connect(url);
                Document document = connect.get();
                webPages.add(new WebPage(uri, document, searchRuleExtractor));
                logger.debug("Create web page for url = '" + url + "'");
            } catch (IOException | URISyntaxException | IllegalArgumentException e) {
                String message = "Not valid url: " + url;
                logger.error(message, e);
                invalidUrls.add(message);
            }
        }
        if (!invalidUrls.isEmpty()) {
            String message = invalidUrls.stream().collect(Collectors.joining("\n"));
            NotValidUrlException e = new NotValidUrlException(message);
            logger.error(message, e);
            throw e;
        }

        return webPages;
    }
}
