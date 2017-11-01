package com.epam.page.object.generator.builder;

import static com.epam.page.object.generator.utils.StringUtils.splitCamelCase;

import com.epam.page.object.generator.model.SearchRule;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.openqa.selenium.support.FindBy;

public class FieldsBuilder implements IFieldsBuilder {

    private Class elementClass;
	private Class annotationClass;
    FieldAnnotationFactory fieldAnnotationFactory;

    public FieldsBuilder(Class elementClass, Class annotationClass, FieldAnnotationFactory fieldAnnotationFactory) {
        this.elementClass = elementClass;
		this.annotationClass = annotationClass;
        this.fieldAnnotationFactory = fieldAnnotationFactory;
    }

    @Override
    public List<FieldSpec> buildField(SearchRule searchRule, String url ) throws IOException {
        fieldAnnotationFactory.setAnnotationClass(annotationClass);


        List<FieldSpec> abstractFields = new ArrayList<>();
        List<String> elementsRequiredValues = searchRule.getRequiredValueFromFoundElement(url);

        for (String elementsRequiredValue : elementsRequiredValues) {
            FieldSpecFactory fieldSpecFactory = new FieldSpecFactory(elementClass,elementsRequiredValue);
            abstractFields.add(fieldSpecFactory.build(fieldAnnotationFactory.buildAnnotation(searchRule,elementsRequiredValue,url)));

        }

        return abstractFields;
    }


    public Class getElementClass() {
        return elementClass;
    }

    public Class getAnnotationClass() {
        return annotationClass;
    }

}