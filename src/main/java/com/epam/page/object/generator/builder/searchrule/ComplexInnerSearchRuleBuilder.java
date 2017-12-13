package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
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
