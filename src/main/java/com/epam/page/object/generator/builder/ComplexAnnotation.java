package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.adapter.JavaPoetAdapter;
import com.epam.page.object.generator.builder.JavaPoetClass.AnnotationMember;
import com.epam.page.object.generator.errors.XpathToCssTransformerException;
import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import com.squareup.javapoet.AnnotationSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jsoup.nodes.Element;
import org.openqa.selenium.support.FindBy;

public class ComplexAnnotation implements JavaAnnotation {

    private SearchRule searchRule;
    private Element element;
    private XpathToCssTransformation xpathToCssTransformation;

    public ComplexAnnotation(SearchRule searchRule,
                             Element element,
                             XpathToCssTransformation xpathToCssTransformation) {
        this.searchRule = searchRule;
        this.element = element;
        this.xpathToCssTransformation = xpathToCssTransformation;
    }

    @Override
    public Class getAnnotationClass() {
        return FindBy.class;
    }

    @Override
    public List<AnnotationMember> getAnnotationMembers()
        throws IOException, XpathToCssTransformerException {
        List<AnnotationMember> innerAnnotations = new ArrayList<>();

        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {

            String requiredValue = innerSearchRule.getRequiredValueFromFoundElement(element);
            String annotationElementName = innerSearchRule.getTitle();
            if (requiredValue != null || innerSearchRule.getTitle() != null) {
                addAnnotationMemberIntoInnerAnnotations(element, innerAnnotations, innerSearchRule,
                    annotationElementName);
            }
        }

        return innerAnnotations;
    }

    private void addAnnotationMemberIntoInnerAnnotations(Element element,
                                                         List<AnnotationMember> innerAnnotations,
                                                         SearchRule innerSearchRule,
                                                         String annotationElementName)
        throws XpathToCssTransformerException, IOException {
        AnnotationMember innerAnnotationMember = getAnnotationMemberFromRule(
            innerSearchRule,
            element);

        AnnotationSpec innerAnnotation = buildAnnotationSpec(FindBy.class,
            Collections.singletonList(innerAnnotationMember));
        innerAnnotations
            .add(new AnnotationMember(annotationElementName, "$L", innerAnnotation));
    }

    private AnnotationSpec buildAnnotationSpec(Class annotationClass,
                                               List<AnnotationMember> annotationMembers) {
        AnnotationSpec annotationSpec = AnnotationSpec.builder(annotationClass).build();

        for (AnnotationMember annotationMember : annotationMembers) {
            if (annotationMember.format.equals("$S")) {
                annotationSpec = annotationSpec.toBuilder()
                    .addMember(annotationMember.name, annotationMember.format, annotationMember.arg)
                    .build();
            } else if (annotationMember.format.equals("$L")) {
                annotationSpec = annotationSpec.toBuilder()
                    .addMember(annotationMember.name, annotationMember.format,
                        annotationMember.annotation)
                    .build();
            }
        }

        return annotationSpec;
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
