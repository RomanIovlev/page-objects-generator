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

public class FieldAnnotationFactory {

    private Class annotationClass;

    private AnnotationsBuilder annotationsBuilder = new AnnotationsBuilder();

    public FieldAnnotationFactory(Class annotationClass) {
        this.annotationClass = annotationClass;
    }

    public AnnotationSpec buildAnnotation(SearchRule searchRule, String elementsRequiredValue, String url) throws IOException {
        if (searchRule.getInnerSearchRules() == null) {
            return annotationsBuilder.buildCommonAnnotation(searchRule, elementsRequiredValue, annotationClass);
        } else {
            return annotationsBuilder.buildComplexAnnotation(searchRule, url, annotationClass);
        }
    }
}