package com.epam.page.object.generator.builder.webpage;

import com.epam.page.object.generator.model.WebPage;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import java.util.List;

public interface WebPageBuilder {

    List<WebPage> generate(List<String> paths, SearchRuleExtractor searchRuleExtractor);
}
