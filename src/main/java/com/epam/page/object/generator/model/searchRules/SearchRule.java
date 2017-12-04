package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.WebElement;
import java.util.List;
import org.jsoup.select.Elements;

public interface SearchRule extends Validatable {
    Selector getSelector();
    List<WebElement> getWebElements(Elements elements);
}
