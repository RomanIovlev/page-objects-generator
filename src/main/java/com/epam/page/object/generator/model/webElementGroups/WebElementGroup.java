package com.epam.page.object.generator.model.webElementGroups;

import com.epam.page.object.generator.adapter.javaFields.IJavaField;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.model.searchRules.Validatable;
import com.epam.page.object.generator.model.webElements.WebElement;
import java.util.List;

public interface WebElementGroup extends Validatable {

    SearchRule getSearchRule();

    List<WebElement> getWebElements();

    List<IJavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                            String packageName);
}
