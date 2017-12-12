package com.epam.page.object.generator.models.webElementGroups;

import com.epam.page.object.generator.adapter.fileds.IJavaField;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.models.searchRules.SearchRule;
import com.epam.page.object.generator.models.searchRules.Validatable;
import com.epam.page.object.generator.models.webElements.WebElement;
import java.util.List;

public interface WebElementGroup extends Validatable {

    SearchRule getSearchRule();

    List<WebElement> getWebElements();

    List<IJavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                            String packageName);
}
