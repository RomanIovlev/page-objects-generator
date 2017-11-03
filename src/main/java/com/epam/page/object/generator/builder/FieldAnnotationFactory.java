package com.epam.page.object.generator.builder;

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

public class FieldAnnotationFactory {

    private Class annotationClass;

    public FieldAnnotationFactory(Class annotationClass) {
        this.annotationClass = annotationClass;
    }

    public AnnotationSpec buildAnnotation(SearchRule searchRule, String elementsRequiredValue, String url) throws IOException {
        if (searchRule.getInnerSearchRules() == null) {
            return buildCommonAnnotation(searchRule, elementsRequiredValue);
        } else {
            return buildComplexAnnotation(searchRule, url);
        }
    }

    private AnnotationSpec buildCommonAnnotation(SearchRule searchRule, String elementsRequiredValue) {
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

    private AnnotationSpec buildComplexAnnotation(SearchRule searchRule, String url) throws IOException {
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

    //Move to separate class
    private String resultCssSelector(SearchRule searchRule, String elementsRequiredValue) {
        return searchRule.getCss() + "[" + searchRule.getRequiredAttribute() + "='" + elementsRequiredValue + "']";
    }

    private String resultXpathSelector(SearchRule searchRule, String elementsRequiredValue) {
        String xpathWithoutCloseBracket= searchRule.getXpath().replace("]", "");

        if (!searchRule.getRequiredAttribute().equalsIgnoreCase("text")) {
            return xpathWithoutCloseBracket + " and @"
                + searchRule.getRequiredAttribute() + "='" + elementsRequiredValue + "']";
        } else {
            return xpathWithoutCloseBracket + " and text()='" + elementsRequiredValue + "']";
        }
    }

}