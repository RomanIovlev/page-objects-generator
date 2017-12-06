package com.epam.page.object.generator.adapter.searchRuleAnnotations;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.util.Collections;
import java.util.List;
import org.jsoup.nodes.Element;
import org.openqa.selenium.support.FindBy;

public class ComplexInnerSearchRuleAnnotation implements IJavaAnnotation {

    private ComplexInnerSearchRule searchRule;
    private Element element;

    public ComplexInnerSearchRuleAnnotation(ComplexInnerSearchRule searchRule, Element element) {
        this.searchRule = searchRule;
        this.element = element;
    }

    @Override
    public Class getAnnotationClass() {
        return FindBy.class;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers() throws XpathToCssTransformerException {
        if (element == null) {
            return Collections
                .singletonList(new AnnotationMember(searchRule.getSelector().getType(), "$S",
                    searchRule.getSelector().getValue()));
        } else {

            return Collections.singletonList(getAnnotationMemberFromRule(searchRule, element));
        }
    }

    private AnnotationMember getAnnotationMemberFromRule(ComplexInnerSearchRule searchRule,
                                                         Element element)
        throws XpathToCssTransformerException {

        String requiredValue = null;

        if (searchRule.isRoot()) {
            requiredValue = searchRule.getUniqueness().equals("text")
                ? element.text()
                : element.attr(searchRule.getUniqueness());
        }

        if (!searchRule.getUniqueness().equalsIgnoreCase("text")) {

            Selector cssSelector = searchRule.getSelector();

            if (cssSelector.isXpath()) {
                cssSelector = XpathToCssTransformation.getCssSelector(cssSelector);
            }

            return new AnnotationMember("css", "$S",
                resultCssSelector(cssSelector, requiredValue, searchRule.getUniqueness()));
        }
        return new AnnotationMember("xpath", "$S",
            resultXpathSelector(searchRule.getSelector(), requiredValue,
                searchRule.getUniqueness()));
    }
}
