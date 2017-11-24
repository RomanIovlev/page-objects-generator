package com.epam.page.object.generator.adapter;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.adapter.JavaPoetClass.AnnotationMember;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.jsoup.nodes.Element;

public class CommonAnnotation implements JavaAnnotation {

    private SearchRule searchRule;
    private Element element;
    private Class fieldAnnotationClass;
    private XpathToCssTransformation xpathToCssTransformation;

    public CommonAnnotation(SearchRule searchRule,
                            Element element,
                            Class fieldAnnotationClass,
                            XpathToCssTransformation xpathToCssTransformation) {
        this.searchRule = searchRule;
        this.element = element;
        this.fieldAnnotationClass = fieldAnnotationClass;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    @Override
    public Class getAnnotationClass() {
        return fieldAnnotationClass;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers()
        throws IOException, XpathToCssTransformerException {
        String requiredValue = searchRule.getRequiredValueFromFoundElement(element);
        if ((requiredValue != null) && (!requiredValue.isEmpty())) {
            return Collections.singletonList(getAnnotationMemberFromRule(
                searchRule, element));
        }
        return null;
    }

    private AnnotationMember getAnnotationMemberFromRule(SearchRule searchRule, Element element)
        throws XpathToCssTransformerException, IOException {
        AnnotationMember annotationMember = null;

        if (searchRule.getRequiredValueFromFoundElement(element) == null) {
            annotationMember = createAnnotationMemberForInnerSearchRule(searchRule);
        } else {
            String elementRequiredValue = searchRule.getRequiredValueFromFoundElement(element);
            if (searchRule.getUniqueness() == null || !searchRule.getUniqueness()
                .equalsIgnoreCase("text")) {
                if (searchRule.getCss() == null) {
                    xpathToCssTransformation.transformRule(searchRule);
                }
                annotationMember = new AnnotationMember("css", "$S",
                    resultCssSelector(searchRule, elementRequiredValue));
            } else {
                annotationMember = new AnnotationMember("xpath", "$S",
                    resultXpathSelector(searchRule, elementRequiredValue));
            }
        }
        return annotationMember;
    }

    private AnnotationMember createAnnotationMemberForInnerSearchRule(SearchRule searchRule) {
        if (searchRule.getXpath() != null) {
            return new AnnotationMember("xpath", "$S",
                resultXpathSelector(searchRule, null));
        } else if (searchRule.getCss() != null) {
            return new AnnotationMember("css", "$S",
                resultCssSelector(searchRule, null));
        }
        return null;
    }
}
