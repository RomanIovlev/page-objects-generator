package com.epam.page.object.generator.builder.searchrule;

import com.epam.page.object.generator.container.SupportedTypesContainer;
import com.epam.page.object.generator.model.ClassAndAnnotationPair;
import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchrule.FormInnerSearchRule;
import com.epam.page.object.generator.model.searchrule.FormSearchRule;
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

public class FormSearchRuleBuilder implements SearchRuleBuilder {

    private final static Logger logger = LoggerFactory.getLogger(FormSearchRuleBuilder.class);

    private RawSearchRuleMapper rawSearchRuleMapper;

    public FormSearchRuleBuilder(
        RawSearchRuleMapper rawSearchRuleMapper) {
        this.rawSearchRuleMapper = rawSearchRuleMapper;
    }

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {

        SearchRuleType type = rawSearchRule.getType();
        String section = rawSearchRule.getValue("section");
        Selector selector = rawSearchRule.getSelector();

        List<FormInnerSearchRule> innerSearchRules = new ArrayList<>();

        logger.info("Create list of formInnerSearchRules...");
        List<RawSearchRule> innerRawSearchRules = rawSearchRuleMapper
            .getFormInnerRawSearchRules(rawSearchRule);
        logger.info("Finish creating list of formInnerSearchRule\n");

        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
            .get(type.getName());

        FormInnerSearchRuleBuilder builder = new FormInnerSearchRuleBuilder();
        for (RawSearchRule innerRawSearchRule : innerRawSearchRules) {
            innerSearchRules.add(
                (FormInnerSearchRule) builder.buildSearchRule(innerRawSearchRule, typesContainer,
                    transformer, selectorUtils, searchRuleExtractor));
        }

        return new FormSearchRule(section, type, selector, innerSearchRules, classAndAnnotation);
    }
}