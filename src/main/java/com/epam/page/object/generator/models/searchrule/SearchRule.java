package com.epam.page.object.generator.models.searchrule;

import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.webgroup.WebElementGroup;
import com.epam.page.object.generator.models.webelement.WebElement;
import java.util.List;
import org.jsoup.select.Elements;

public interface SearchRule extends Validatable {

    Selector getSelector();

    List<WebElement> getWebElements(Elements elements);

    void fillWebElementGroup(List<WebElementGroup> webElementGroups,
                             Elements elements);
}
