package com.epam.page.object.generator.models.searchRules;

import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.webElementGroups.WebElementGroup;
import com.epam.page.object.generator.models.webElements.WebElement;
import java.util.List;
import org.jsoup.select.Elements;

public interface SearchRule extends Validatable {

    Selector getSelector();

    List<WebElement> getWebElements(Elements elements);

    void fillWebElementGroup(List<WebElementGroup> webElementGroups,
                             Elements elements);
}
