package com.epam.page.object.generator.model.webgroup;

import com.epam.page.object.generator.adapter.filed.IJavaField;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.model.searchrule.Validatable;
import com.epam.page.object.generator.model.webelement.WebElement;
import java.util.List;

public interface WebElementGroup extends Validatable {

    SearchRule getSearchRule();

    List<WebElement> getWebElements();

    List<IJavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                            String packageName);
}
