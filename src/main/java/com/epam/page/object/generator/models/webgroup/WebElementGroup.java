package com.epam.page.object.generator.models.webgroup;

import com.epam.page.object.generator.adapter.fileds.IJavaField;
import com.epam.page.object.generator.builders.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.models.searchrule.SearchRule;
import com.epam.page.object.generator.models.searchrule.Validatable;
import com.epam.page.object.generator.models.webelement.WebElement;
import java.util.List;

public interface WebElementGroup extends Validatable {

    SearchRule getSearchRule();

    List<WebElement> getWebElements();

    List<IJavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                            String packageName);
}
