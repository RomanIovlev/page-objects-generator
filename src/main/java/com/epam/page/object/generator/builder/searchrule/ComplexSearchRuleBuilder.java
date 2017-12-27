package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.ComplexSearchRule;
import com.epam.page.object.generator.model.searchrule.SearchRule;
import com.epam.page.object.generator.util.RawSearchRuleMapper;
import com.epam.page.object.generator.util.SearchRuleExtractor;
import com.epam.page.object.generator.util.SearchRuleType;
import com.epam.page.object.generator.util.SelectorUtils;
import com.epam.page.object.generator.util.XpathToCssTransformer;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is needed for creating {@link ComplexSearchRule} which includes {@link
 * ComplexInnerSearchRule} from {@link RawSearchRule}
 */
public class ComplexSearchRuleBuilder implements SearchRuleBuilder {

    private final static Logger logger = LoggerFactory.getLogger(ComplexSearchRuleBuilder.class);

    private RawSearchRuleMapper rawSearchRuleMapper;
    private ComplexInnerSearchRuleBuilder builder;

    public ComplexSearchRuleBuilder(RawSearchRuleMapper rawSearchRuleMapper,
                                    ComplexInnerSearchRuleBuilder builder) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
        this.builder = builder;
    }

    /**
     * This method builds {@link ComplexSearchRule} getting the necessary information about {@link
     * RawSearchRule} such as {@link RawSearchRule#type}. Then based on this parameter get {@link
     * ClassAndAnnotationPair}. After it looking for {@link ComplexInnerSearchRule} in {@link
     * RawSearchRule} and build it. At last sent {@link RawSearchRule#type}, {@link
     * ComplexInnerSearchRule}, {@link ClassAndAnnotationPair} and {@link SelectorUtils} in
     * constructor and get new {@link ComplexSearchRule}
     *
     * @return {@link ComplexSearchRule}
     */
    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {
        logger.debug("Start transforming of " + rawSearchRule);
        SearchRuleType type = rawSearchRule.getType();
        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());

        List<ComplexInnerSearchRule> innerSearchRules = new ArrayList<>();

        logger.debug("Create list of complexInnerSearchRules...");
        List<RawSearchRule> innerRawSearchRules = rawSearchRuleMapper
            .getComplexInnerRawSearchRules(rawSearchRule);

        for (RawSearchRule innerRawSearchRule : innerRawSearchRules) {
            innerSearchRules.add((ComplexInnerSearchRule) builder
                .buildSearchRule(innerRawSearchRule, typesContainer, transformer, selectorUtils,
                    searchRuleExtractor));
        }
        logger.debug("Finish creating list of complexInnerSearchRules");

        ComplexSearchRule complexSearchRule = new ComplexSearchRule(type, innerSearchRules,
            classAndAnnotation, selectorUtils);
        logger.debug("Create a new " + complexSearchRule + "\n");
        return complexSearchRule;
    }
}
