package com.epam.page.object.generator.adapter.searchRuleAnnotations;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.utils.SelectorUtils;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.util.Collections;
import java.util.List;
import org.jsoup.nodes.Element;

public class CommonSearchRuleAnnotation implements JavaAnnotation {

    private Class fieldAnnotationClass;
    private CommonSearchRule searchRule;
    private Element element;

    public CommonSearchRuleAnnotation(CommonSearchRule searchRule, Element element,
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
    public List<AnnotationMember> getAnnotationMembers()
        throws XpathToCssTransformerException {

        Selector resultSelector = SelectorUtils.resultSelector(searchRule.getSelector(), element);

        return Collections.singletonList(new AnnotationMember(resultSelector.getType(), "$S", resultSelector.getValue()));
    }

    private AnnotationMember getAnnotationMemberFromRule(CommonSearchRule searchRule, Element element)
        throws XpathToCssTransformerException {

        String requiredValue = searchRule.getRequiredValue(element);

        if (!searchRule.getUniqueness().equalsIgnoreCase("text")) {

            if (searchRule.getSelector().isXpath()) {
                XpathToCssTransformation.getCssSelector(searchRule);
            }

            return new AnnotationMember("css", "$S", resultCssSelector(searchRule, requiredValue));
        }
        return new AnnotationMember("xpath", "$S", resultXpathSelector(searchRule, requiredValue));
    }
}
