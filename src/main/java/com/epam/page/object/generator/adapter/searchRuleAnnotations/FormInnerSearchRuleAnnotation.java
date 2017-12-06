package com.epam.page.object.generator.adapter.searchRuleAnnotations;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.util.Collections;
import java.util.List;
import org.jsoup.nodes.Element;

public class FormInnerSearchRuleAnnotation implements IJavaAnnotation {

    private FormInnerSearchRule searchRule;
    private Element element;
    private Class fieldAnnotationClass;

    public FormInnerSearchRuleAnnotation(FormInnerSearchRule searchRule, Element element,
                                         Class fieldAnnotationClass) {
        this.searchRule = searchRule;
        this.element = element;
        this.fieldAnnotationClass = fieldAnnotationClass;
    }

    @Override
    public Class getAnnotationClass() {
        return fieldAnnotationClass;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers() throws XpathToCssTransformerException {
        return Collections.singletonList(getAnnotationMemberFromRule(searchRule, element));
    }

    private AnnotationMember getAnnotationMemberFromRule(FormInnerSearchRule searchRule,
                                                         Element element)
        throws XpathToCssTransformerException {

        String requiredValue = searchRule.getRequiredValue(element);

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