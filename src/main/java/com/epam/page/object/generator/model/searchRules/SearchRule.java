package com.epam.page.object.generator.model.searchRules;

import com.epam.page.object.generator.model.Selector;

public interface SearchRule extends Validatable {
    Selector getSelector();
}
