package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;

public interface SearchRuleBuilder {

    SearchRule buildSearchRule(RawSearchRule rawSearchRule, SupportedTypesContainer typesContainer);
}
