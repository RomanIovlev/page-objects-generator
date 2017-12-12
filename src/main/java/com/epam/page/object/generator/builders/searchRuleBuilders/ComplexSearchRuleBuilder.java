package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.models.ClassAndAnnotationPair;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.models.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.models.searchRules.SearchRule;
import com.epam.page.object.generator.utils.RawSearchRuleMapper;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
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
                                      SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {
        logger.debug("Start transforming of " + rawSearchRule);
        SearchRuleType type = rawSearchRule.getType();
        logger.debug("'type' = " + type);
        List<ComplexInnerSearchRule> innerSearchRules = new ArrayList<>();

        logger.info("Create list of complexInnerSearchRules...");
        List<RawSearchRule> innerRawSearchRules = rawSearchRuleMapper
            .getComplexInnerRawSearchRules(rawSearchRule);
        logger.info("Finish creating list of complexInnerSearchRules");

        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());
        logger.debug("'class' = " + classAndAnnotation.getElementClass().getName());
        logger.debug("'annotation' = " + classAndAnnotation.getElementAnnotation().getName());

        for (RawSearchRule innerRawSearchRule : innerRawSearchRules) {
            innerSearchRules.add((ComplexInnerSearchRule) builder
                .buildSearchRule(innerRawSearchRule, typesContainer, transformer, selectorUtils,
                    searchRuleExtractor));
        }

        ComplexSearchRule complexSearchRule = new ComplexSearchRule(type, innerSearchRules,
            classAndAnnotation, selectorUtils);
        logger.debug("Create a new " + complexSearchRule + "\n");
        return complexSearchRule;
    }
}
