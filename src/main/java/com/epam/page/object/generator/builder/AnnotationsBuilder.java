package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.model.SearchRule;
import com.epam.page.object.generator.utils.XpathToCssTransformer;
import com.squareup.javapoet.AnnotationSpec;
import java.io.IOException;
import org.openqa.selenium.support.FindBy;

public class AnnotationsBuilder {

    public AnnotationSpec buildCommonAnnotation(SearchRule searchRule, String elementsRequiredValue, Class annotationClass) {
        if (!searchRule.getRequiredAttribute().equalsIgnoreCase("text")) {
            if (searchRule.getCss() == null) {
                XpathToCssTransformer.transformRule(searchRule);
            }

            return AnnotationSpec.builder(annotationClass)
                .addMember("css", "$S", resultCssSelector(searchRule, elementsRequiredValue))
                .build();
        } else {
            return AnnotationSpec.builder(annotationClass)
                .addMember("xpath", "$S", resultXpathSelector(searchRule, elementsRequiredValue))
                .build();
        }
    }

    public AnnotationSpec buildComplexAnnotation(SearchRule searchRule, String url, Class annotationClass) throws IOException {
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec.builder(annotationClass);
        String searchAttribute = searchRule.getRequiredAttribute();

        for (SearchRule innerSearchRule : searchRule.getInnerSearchRules()) {
            AnnotationSpec.Builder innerAnnotation = AnnotationSpec.builder(FindBy.class);
            String annotationElementName = innerSearchRule.getRequiredAttribute();

            innerSearchRule.setRequiredAttribute(searchAttribute);

            if (innerSearchRule.getCss() != null) {
                innerAnnotation.addMember("css", "$S", resultCssSelector(innerSearchRule,
                    innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
            } else {
                innerAnnotation.addMember("xpath", "$S", resultXpathSelector(innerSearchRule,
                    innerSearchRule.getRequiredValueFromFoundElement(url).get(0)));
            }

            annotationBuilder.addMember(annotationElementName, "$L", innerAnnotation.build());
        }

        return annotationBuilder.build();
    }
}
