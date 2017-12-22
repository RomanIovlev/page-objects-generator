package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.CommonSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
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
        SearchRuleType type = rawSearchRule.getType();
        Selector selector = rawSearchRule.getSelector();
        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());

        CommonSearchRule commonSearchRule = new CommonSearchRule(uniqueness, type, selector,
            classAndAnnotation, transformer,
            selectorUtils);
        logger.debug("Create a new " + commonSearchRule);

        return commonSearchRule;
    }
}
