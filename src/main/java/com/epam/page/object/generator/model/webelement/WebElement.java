package com.epam.page.object.generator.model.webelement;

import org.jsoup.nodes.Element;

public interface WebElement {

    Element getElement();

    String getUniquenessValue();
}
