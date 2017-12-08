package com.epam.page.object.generator.builders.searchRuleBuilders;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexSearchRuleBuilder implements SearchRuleBuilder {

    private final static Logger logger = LoggerFactory.getLogger(ComplexSearchRuleBuilder.class);

    private RawSearchRuleMapper rawSearchRuleMapper;
    private ComplexInnerSearchRuleBuilder builder;

    public ComplexSearchRuleBuilder(RawSearchRuleMapper rawSearchRuleMapper,
                                    ComplexInnerSearchRuleBuilder builder) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
        this.builder = builder;
    }

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer) {

        SearchRuleType type = rawSearchRule.getType();
        List<ComplexInnerSearchRule> innerSearchRules = new ArrayList<>();

        logger.info("Create list of complexInnerSearchRules...");
        List<RawSearchRule> innerRawSearchRules = rawSearchRuleMapper
            .getComplexInnerRawSearchRules(rawSearchRule);
        logger.info("Finish creating list of complexInnerSearchRules\n");

        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());

        for (RawSearchRule innerRawSearchRule : innerRawSearchRules) {
            innerSearchRules.add((ComplexInnerSearchRule) builder
                .buildSearchRule(innerRawSearchRule, typesContainer));
        }

        return new ComplexSearchRule(type, innerSearchRules, classAndAnnotation);
    }
}
