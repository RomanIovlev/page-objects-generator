package com.epam.page.object.generator.model.webElementGroups;

import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.searchRules.Validatable;
import com.epam.page.object.generator.model.webElements.WebElement;
import java.util.List;

public interface WebElementGroup extends Validatable {

    SearchRule getSearchRule();

    List<WebElement> getWebElements();

    boolean isJavaClass();
}