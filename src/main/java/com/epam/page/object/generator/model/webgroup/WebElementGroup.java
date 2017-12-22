package com.epam.page.object.generator.model.webgroup;

import com.epam.page.object.generator.adapter.JavaField;
import com.epam.page.object.generator.builder.WebElementGroupFieldBuilder;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.model.searchrule.Validatable;
import com.epam.page.object.generator.model.webelement.WebElement;
import java.util.List;

public interface WebElementGroup extends Validatable {

    SearchRule getSearchRule();

    List<WebElement> getWebElements();

    List<JavaField> accept(WebElementGroupFieldBuilder webElementGroupFieldBuilder,
                           String packageName);
}
