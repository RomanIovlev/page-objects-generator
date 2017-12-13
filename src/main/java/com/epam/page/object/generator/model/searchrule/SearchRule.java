package com.epam.page.object.generator.model.searchrule;

import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.webgroup.WebElementGroup;
import com.epam.page.object.generator.model.webelement.WebElement;
import java.util.List;
import org.jsoup.select.Elements;

public interface SearchRule extends Validatable {

    Selector getSelector();

    List<WebElement> getWebElements(Elements elements);

    void fillWebElementGroup(List<WebElementGroup> webElementGroups,
                             Elements elements);
}
