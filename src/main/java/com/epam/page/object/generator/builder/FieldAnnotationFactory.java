package com.epam.page.object.generator.builder;

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.AnnotationSpec;
import java.io.IOException;
import org.openqa.selenium.support.FindBy;

public class FieldAnnotationFactory {

    private Class annotationClass;


    public AnnotationSpec buildAnnotation(SearchRule searchRule, String elementsRequiredValue,
                                          String url) throws IOException {
        if (searchRule.getInnerSearchRules() == null) {
            return buildCommonAnnotation(searchRule, elementsRequiredValue);
        } else {
            return buildComplexAnnotation(searchRule, url);
        }
    }

    private AnnotationSpec buildCommonAnnotation(SearchRule searchRule,
                                                 String elementsRequiredValue) {
        if (searchRule.getCss() != null) {
            return AnnotationSpec.builder(annotationClass)
                .addMember("css", "$S", resultCssSelector(searchRule, elementsRequiredValue))
                .build();
        } else {
            return AnnotationSpec.builder(annotationClass)
                .addMember("xpath", "$S", resultXpathSelector(searchRule, elementsRequiredValue))
                .build();
        }
    }

    private AnnotationSpec buildComplexAnnotation(SearchRule searchRule, String url)
        throws IOException {
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec.builder(annotationClass);
        String searchAttribute = searchRule.getRequiredAttribute();

        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
            AnnotationSpec.Builder innerAnnotation = AnnotationSpec.builder(FindBy.class);
            String annotationElementName = innerSearchRule.getTitle();

            if (innerSearchRule.getCss() != null) {
                innerAnnotation.addMember("css", "$S", resultCssSelector(innerSearchRule,
                    innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
            } else if (innerSearchRule.getXpath() != null) {
                innerAnnotation.addMember("xpath", "$S", resultXpathSelector(innerSearchRule,
                    innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
            }

            annotationBuilder.addMember(annotationElementName, "$L", innerAnnotation.build());
        }

        return annotationBuilder.build();
    }

    private String resultCssSelector(SearchRule searchRule, String elementsRequiredValue) {
        return searchRule.getCss() + "[" + searchRule.getRequiredAttribute() + "='"
            + elementsRequiredValue + "']";
    }

    private String resultXpathSelector(SearchRule searchRule, String elementsRequiredValue) {
        String xpathWithoutCloseBracket = searchRule.getXpath().replace("]", "");

        if (searchRule.getInnerSearchRules() == null) {

            if (!searchRule.getRequiredAttribute().equalsIgnoreCase("text")) {
                return xpathWithoutCloseBracket + " and @"
                    + searchRule.getRequiredAttribute() + "='" + elementsRequiredValue + "']";
            } else {
                return xpathWithoutCloseBracket + " and text()='" + elementsRequiredValue + "']";
            }
        }

        return "";
    }


    public void setAnnotationClass(Class annotationClass) {
        this.annotationClass = annotationClass;
    }
}
