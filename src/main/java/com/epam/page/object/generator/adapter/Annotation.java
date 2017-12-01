package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import org.jsoup.nodes.Element;

public abstract class Annotation implements JavaAnnotation {

    protected SearchRule searchRule;
    protected Element element;
    protected XpathToCssTransformation xpathToCssTransformation;

    public Annotation(SearchRule searchRule, Element element,
                      XpathToCssTransformation xpathToCssTransformation) {
        this.searchRule = searchRule;
        this.element = element;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    public AnnotationMember getAnnotationMemberFromRule(SearchRule searchRule, Element element)
        throws XpathToCssTransformerException {

        if (searchRule.getRequiredValue(element) == null) {
            return createAnnotationMemberForInnerSearchRule(searchRule);
        }
        String elementRequiredValue = searchRule.getRequiredValue(element);
        if (searchRule.getUniqueness() == null || !searchRule.getUniqueness()
            .equalsIgnoreCase("text")) {
            if (searchRule.getCss() == null) {
                xpathToCssTransformation.getCssSelector(searchRule);
            }
            return new AnnotationMember("css", "$S",
                resultCssSelector(searchRule, elementRequiredValue));
        }
        return new AnnotationMember("xpath", "$S",
            resultXpathSelector(searchRule, elementRequiredValue));
    }

    private AnnotationMember createAnnotationMemberForInnerSearchRule(SearchRule searchRule) {
        Selector selector = searchRule.getSelector();
        if (selector.isXpath()) {
            return new AnnotationMember("xpath", "$S",
                resultXpathSelector(searchRule, null));
        } else if (selector.isCss()) {
            return new AnnotationMember("css", "$S",
                resultCssSelector(searchRule, null));
        }
        return null;
    }

}
