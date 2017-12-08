package com.epam.page.object.generator.builders.searchRuleBuilders;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SearchRuleType;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonSearchRuleBuilder implements SearchRuleBuilder {

    private final static Logger logger = LoggerFactory.getLogger(CommonSearchRuleBuilder.class);

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {
        logger.debug("Start transforming of " + rawSearchRule);
        String uniqueness = rawSearchRule.getValue("uniqueness");
        logger.debug("'uniqueness' = " + uniqueness);
        SearchRuleType type = rawSearchRule.getType();
        logger.debug("'type' = " + type);
        Selector selector = rawSearchRule.getSelector();
        logger.debug("'selector' = " + selector);
        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());
        logger.debug("'class' = " + classAndAnnotation.getElementClass().getName());
        logger.debug("'annotation' = " + classAndAnnotation.getElementAnnotation().getName());

        CommonSearchRule commonSearchRule = new CommonSearchRule(uniqueness, type, selector,
            classAndAnnotation, transformer,
            selectorUtils);
        logger.debug("Create a new " + commonSearchRule + "\n");

        return commonSearchRule;
    }
}
