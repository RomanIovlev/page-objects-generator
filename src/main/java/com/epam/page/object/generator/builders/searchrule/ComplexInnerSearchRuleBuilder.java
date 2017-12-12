package com.epam.page.object.generator.builders.searchrule;

import com.epam.page.object.generator.containers.SupportedTypesContainer;
import com.epam.page.object.generator.models.RawSearchRule;
import com.epam.page.object.generator.models.Selector;
import com.epam.page.object.generator.models.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.models.searchrule.SearchRule;
import com.epam.page.object.generator.utils.SearchRuleExtractor;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexInnerSearchRuleBuilder implements SearchRuleBuilder {

    private final static Logger logger = LoggerFactory.getLogger(ComplexInnerSearchRuleBuilder.class);

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {
        logger.debug("\nStart transforming of " + rawSearchRule);
        String title = rawSearchRule.getValue("title");
        logger.debug("'title' = " + title);

        String uniqueness = null;
        if (title.equals("root")) {
            uniqueness = rawSearchRule.getValue("uniqueness");
            logger.debug("'uniqueness' = " + uniqueness);
        }

        Selector selector = rawSearchRule.getSelector();
        logger.debug("'selector' = " + selector);

        ComplexInnerSearchRule complexInnerSearchRule = new ComplexInnerSearchRule(uniqueness,
            title, selector, transformer);
        logger.debug("Create a new innerSearchRule " + complexInnerSearchRule);
        return complexInnerSearchRule;
    }
}
