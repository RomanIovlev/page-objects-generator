package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.builders.RawSearchRuleBuilder;
import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleType;
import java.util.ArrayList;
import java.util.List;

public class ComplexSearchRuleBuilder extends RawSearchRuleBuilder {

    private RawSearchRuleMapper rawSearchRuleMapper;

    public ComplexSearchRuleBuilder(
        RawSearchRuleMapper rawSearchRuleMapper) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
    }

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer) {

        SearchRuleType type = rawSearchRule.getType();
        List<ComplexInnerSearchRule> innerSearchRules = new ArrayList<>();

        List<RawSearchRule> innerRawSearchRules = rawSearchRuleMapper
            .getComplexInnerRawSearchRules(rawSearchRule);

        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());

        ComplexInnerSearchRuleBuilder builder = new ComplexInnerSearchRuleBuilder();
        for (RawSearchRule innerRawSearchRule : innerRawSearchRules) {
            innerSearchRules.add(
                (ComplexInnerSearchRule) builder
                    .buildSearchRule(innerRawSearchRule, typesContainer));
        }

        return new ComplexSearchRule(type, innerSearchRules, classAndAnnotation);
    }
}
