package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.webSearchRules.WebSearchRule;
import org.jsoup.select.Elements;

public interface SearchRule extends Validatable {
    Selector getSelector();
    WebSearchRule getWebSearchRule(Elements elements);
}
