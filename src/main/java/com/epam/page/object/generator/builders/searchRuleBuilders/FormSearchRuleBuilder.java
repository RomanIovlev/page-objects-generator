package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.ArrayList;
import java.util.List;

public class FormSearchRuleBuilder implements SearchRuleBuilder {

    private RawSearchRuleMapper rawSearchRuleMapper;

    public FormSearchRuleBuilder(
        RawSearchRuleMapper rawSearchRuleMapper) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
    }

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer) {

        SearchRuleType type = rawSearchRule.getType();
        String section = rawSearchRule.getValue("section");
        Selector selector = rawSearchRule.getSelector();

        List<FormInnerSearchRule> innerSearchRules = new ArrayList<>();

        List<RawSearchRule> innerRawSearchRules = rawSearchRuleMapper
            .getFormInnerRawSearchRules(rawSearchRule);

        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());

        FormInnerSearchRuleBuilder builder = new FormInnerSearchRuleBuilder();
        for (RawSearchRule innerRawSearchRule : innerRawSearchRules) {
            innerSearchRules.add(
                (FormInnerSearchRule) builder.buildSearchRule(innerRawSearchRule, typesContainer));
        }

        return new FormSearchRule(section, type, selector, innerSearchRules, classAndAnnotation);
    }
}
