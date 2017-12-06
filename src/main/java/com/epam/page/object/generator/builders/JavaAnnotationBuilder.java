package com.epam.page.object.generator.builders;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.adapter.IJavaAnnotation;
import com.epam.page.object.generator.adapter.IJavaAnnotation.AnnotationMember;
import com.epam.page.object.generator.adapter.JavaAnnotation;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.FormInnerSearchRule;
import com.epam.page.object.generator.model.webElements.CommonWebElement;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexInnerSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;
import com.epam.page.object.generator.model.webElements.ComplexWebElement;
import com.epam.page.object.generator.model.webElements.FormWebElement;
import com.epam.page.object.generator.model.webElements.WebElement;
import com.epam.page.object.generator.utils.XpathToCssTransformation;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.support.FindBy;

public class JavaAnnotationBuilder {

    public IJavaAnnotation buildAnnotation(Class annotationClass, CommonWebElement webElement,
                                           CommonSearchRule searchRule) {

        List<AnnotationMember> annotationMembers = new ArrayList<>();

        String uniquenessValue = webElement.getUniquenessValue();
        Selector selector = searchRule.getTransformedSelector();
        String annotationValue = getAnnotationValue(selector, uniquenessValue,
            searchRule.getUniqueness());

        annotationMembers.add(new AnnotationMember(selector.getType(), "$S", annotationValue));

        return new JavaAnnotation(annotationClass, annotationMembers);
    }

    public IJavaAnnotation buildAnnotation(Class annotationClass, ComplexWebElement webElement,
                                           ComplexSearchRule searchRule) {

        List<AnnotationMember> annotationMembers = new ArrayList<>();

        for (ComplexInnerSearchRule innerSearchRule : searchRule.getInnerSearchRules()) {

            String annotationElementName = innerSearchRule.getTitle();
            IJavaAnnotation innerSearchRuleAnnotation;

            if (annotationElementName.equals("root")) {
                Selector selector = innerSearchRule.getTransformedSelector();
                String uniquenessValue = webElement.getUniquenessValue();
                String annotationValue = getAnnotationValue(selector, uniquenessValue,
                    searchRule.getUniqueness());

                List<AnnotationMember> innerAnnotation = new ArrayList<>();
                innerAnnotation
                    .add(new AnnotationMember(selector.getType(), "$S", annotationValue));

                innerSearchRuleAnnotation = new JavaAnnotation(FindBy.class, innerAnnotation);
            } else {
                Selector selector = innerSearchRule.getSelector();

                List<AnnotationMember> innerAnnotation = new ArrayList<>();
                innerAnnotation
                    .add(new AnnotationMember(selector.getType(), "$S", selector.getValue()));

                innerSearchRuleAnnotation = new JavaAnnotation(FindBy.class, innerAnnotation);
            }

            annotationMembers
                .add(new AnnotationMember(annotationElementName, "$L",
                    innerSearchRuleAnnotation));
        }

        return new JavaAnnotation(annotationClass, annotationMembers);
    }

    public IJavaAnnotation buildAnnotation(Class annotationClass, FormSearchRule searchRule) {
        List<AnnotationMember> annotationMembers = new ArrayList<>();

        annotationMembers.add(new AnnotationMember(searchRule.getSelector().getType(), "$S",
            searchRule.getSelector().getValue()));

        return new JavaAnnotation(annotationClass, annotationMembers);
    }

    public IJavaAnnotation buildAnnotation(Class annotationClass, FormWebElement webElement,
                                           FormInnerSearchRule searchRule) {
        List<AnnotationMember> annotationMembers = new ArrayList<>();

        String uniquenessValue = webElement.getUniquenessValue();
        Selector selector = searchRule.getTransformedSelector();
        String annotationValue = getAnnotationValue(selector, uniquenessValue,
            searchRule.getUniqueness());

        annotationMembers.add(new AnnotationMember(selector.getType(), "$S", annotationValue));

        return new JavaAnnotation(annotationClass, annotationMembers);
    }

    private String getAnnotationValue(Selector selector, String uniquenessValue,
                                      String uniqueness) {
        if (selector.isXpath()) {
            return resultXpathSelector(selector, uniquenessValue, uniqueness);
        } else if (selector.isCss()) {
            return resultCssSelector(selector, uniquenessValue, uniqueness);
        }
        return null;
    }
}
