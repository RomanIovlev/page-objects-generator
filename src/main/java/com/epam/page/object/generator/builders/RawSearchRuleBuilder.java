package com.epam.page.object.generator.builders;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;

public abstract class RawSearchRuleBuilder {

    public abstract SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                               SupportedTypesContainer typesContainer);
}
