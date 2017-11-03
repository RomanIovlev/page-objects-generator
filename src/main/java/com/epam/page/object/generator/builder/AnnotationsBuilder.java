package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.SelectorUtils.resultCssSelector;
import static com.epam.page.object.generator.utils.SelectorUtils.resultXpathSelector;

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.AnnotationSpec;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.openqa.selenium.support.FindBy;

public class AnnotationsBuilder {

    public AnnotationSpec buildCommonAnnotation(SearchRule searchRule, String elementsRequiredValue, Class annotationClass) {
        if (!searchRule.getRequiredAttribute().equalsIgnoreCase("text")) {
            if (searchRule.getCss() == null) {
                //TODO extract to XpathToCssTransformer class
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

                try {
                    engine.eval(new FileReader("src/main/resources/cssify.js"));

                    Invocable invocable = (Invocable) engine;

                    searchRule.setCss(invocable.invokeFunction("cssify", searchRule.getXpath()).toString());
                    searchRule.setXpath(null);
                } catch (NoSuchMethodException | ScriptException | FileNotFoundException e) {
                    e.printStackTrace();
                }
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
