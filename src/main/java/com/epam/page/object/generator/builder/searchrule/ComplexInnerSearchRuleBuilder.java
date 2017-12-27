package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is needed for creating {@link ComplexInnerSearchRule} from {@link RawSearchRule}
 */
public class ComplexInnerSearchRuleBuilder implements SearchRuleBuilder {

    private final static Logger logger = LoggerFactory
        .getLogger(ComplexInnerSearchRuleBuilder.class);

    /**
     * This method builds {@link ComplexInnerSearchRule} getting the necessary information about
     * {@link RawSearchRule} such as {@link RawSearchRule#type}, {@link Selector}, uniqueness
     * parameter (in this search rule looking for a unique parameter occurs only in the root
     * element). Then based on {@link RawSearchRule#type} get {@link ClassAndAnnotationPair}. At
     * last sent this parameters plus {@link XpathToCssTransformer} in constructor and get new
     * {@link ComplexInnerSearchRule}
     *
     * @return {@link ComplexInnerSearchRule}
     */
    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {
        logger.debug("Start transforming of " + rawSearchRule);
        String title = rawSearchRule.getValue("title");

        String uniqueness = null;
        if ("root".equals(title)) {
            uniqueness = rawSearchRule.getValue("uniqueness");
        }

        Selector selector = rawSearchRule.getSelector();

        ComplexInnerSearchRule complexInnerSearchRule = new ComplexInnerSearchRule(uniqueness,
            title, selector, transformer);
        logger.debug("Create a new innerSearchRule " + complexInnerSearchRule);
        return complexInnerSearchRule;
    }
}
