package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import org.jsoup.nodes.Element;

public interface SearchRule extends Validatable {
    Selector getSelector();
    String getRequiredValue(Element element);
}
