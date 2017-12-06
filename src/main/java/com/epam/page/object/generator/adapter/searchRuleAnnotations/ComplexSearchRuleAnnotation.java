package com.epam.page.object.generator.adapter.searchRuleAnnotations;

import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

public class ComplexSearchRuleAnnotation implements IJavaAnnotation {

    private ComplexSearchRule searchRule;
    private Element element;
    private Class fieldAnnotationClass;

    public ComplexSearchRuleAnnotation(ComplexSearchRule searchRule, Element element,
                                       Class fieldAnnotationClass) {
        this.searchRule = searchRule;
        this.element = element;
        this.fieldAnnotationClass = fieldAnnotationClass;
    }

    public Class getAnnotationClass() {
        return fieldAnnotationClass;
    }

    public List<AnnotationMember> getAnnotationMembers()
        throws XpathToCssTransformerException {

        List<AnnotationMember> innerAnnotations = new ArrayList<>();

        for (ComplexInnerSearchRule innerSearchRule : searchRule.getInnerSearchRules()) {

            String annotationElementName = innerSearchRule.getTitle();
            ComplexInnerSearchRuleAnnotation innerSearchRuleAnnotation;

            if (annotationElementName.equals("root")) {
                innerSearchRuleAnnotation = new ComplexInnerSearchRuleAnnotation(innerSearchRule,
                    element);
            } else {
                innerSearchRuleAnnotation = new ComplexInnerSearchRuleAnnotation(innerSearchRule,
                    null);

            }

            innerAnnotations
                .add(new AnnotationMember(annotationElementName, "$L",
                    innerSearchRuleAnnotation));
        }
        return innerAnnotations;
    }
}
