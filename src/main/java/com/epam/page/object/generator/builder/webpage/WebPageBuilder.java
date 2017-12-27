package com.epam.page.object.generator.builder.webpage;

import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import java.util.List;
import org.jsoup.nodes.Element;

/**
 * Interface is needed for creates two types webPageBuilders: {@link LocalWebPageBuilder} and {@link
 * UrlWebPageBuilder}.
 *
 * @see LocalWebPageBuilder
 * @see UrlWebPageBuilder
 */
public interface WebPageBuilder {

    /**
     * Main method which generates list of {@link WebPage}.
     *
     * @param paths list of string which can represents paths of local .html files or urls of
     * websites.
     * @param searchRuleExtractor {@link SearchRuleExtractor} allows {@link WebPage} extract {@link
     * Element} from website.
     * @return list of {@link WebPage}
     */
    List<WebPage> generate(List<String> paths, SearchRuleExtractor searchRuleExtractor);
}
